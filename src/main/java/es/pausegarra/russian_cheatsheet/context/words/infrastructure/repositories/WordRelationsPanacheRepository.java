package es.pausegarra.russian_cheatsheet.context.words.infrastructure.repositories;

import es.pausegarra.russian_cheatsheet.common.domain.exception.BadRequest;
import es.pausegarra.russian_cheatsheet.context.words.domain.exception.WordNotFound;
import es.pausegarra.russian_cheatsheet.context.words.domain.exception.WordRelationNotFound;
import es.pausegarra.russian_cheatsheet.context.words.domain.entities.RelatedWordEntity;
import es.pausegarra.russian_cheatsheet.context.words.domain.entities.WordRelationEntity;
import es.pausegarra.russian_cheatsheet.context.words.domain.entities.WordRelationWriteResult;
import es.pausegarra.russian_cheatsheet.context.words.domain.repositories.WordRelationsRepository;
import es.pausegarra.russian_cheatsheet.context.words.infrastructure.models.WordModel;
import es.pausegarra.russian_cheatsheet.context.words.infrastructure.models.WordRelationModel;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@ApplicationScoped
@RequiredArgsConstructor
public class WordRelationsPanacheRepository implements WordRelationsRepository, PanacheRepository<WordRelationModel> {

  private final EntityManager entityManager;

  @Override
  @Transactional
  public WordRelationWriteResult createOutgoing(WordRelationEntity relation) {
    if (relation == null || relation.sourceWordId() == null || relation.relatedWordId() == null
      || relation.relation() == null) {
      throw new BadRequest("Relation source, target, and type are required");
    }

    UUID sourceWordId = relation.sourceWordId();
    WordModel sourceWord = entityManager.find(WordModel.class, sourceWordId);
    if (sourceWord == null) {
      throw new WordNotFound(sourceWordId.toString());
    }
    if (sourceWord.getExternalId() == null) {
      throw new BadRequest("Only imported words can have imported relations");
    }
    if (sourceWordId.equals(relation.relatedWordId())) {
      throw new BadRequest("A word cannot be related to itself");
    }

    WordModel targetWord = entityManager.find(WordModel.class, relation.relatedWordId());
    if (targetWord == null) {
      throw new WordNotFound(relation.relatedWordId().toString());
    }
    if (targetWord.getExternalId() == null) {
      throw new BadRequest("Relation targets must be imported words");
    }

    UUID candidateRelationId = UUID.randomUUID();
    UUID storedRelationId = (UUID) entityManager.createNativeQuery(
        "insert into word_relations (id, source_word_id, target_word_id, relation) " +
          "values (:relationId, :sourceWordId, :targetWordId, :relationType) " +
          "on conflict (source_word_id, target_word_id, relation) " +
          "do update set relation = excluded.relation " +
          "returning id"
      )
      .setParameter("relationId", candidateRelationId)
      .setParameter("sourceWordId", sourceWordId)
      .setParameter("targetWordId", relation.relatedWordId())
      .setParameter("relationType", relation.relation().value())
      .getSingleResult();

    WordRelationModel saved = entityManager.find(WordRelationModel.class, storedRelationId);
    return new WordRelationWriteResult(candidateRelationId.equals(storedRelationId), relatedWord(saved));
  }

  @Override
  public List<RelatedWordEntity> findOutgoing(UUID sourceWordId) {
    return entityManager.createQuery(
        "select relation from WordRelationModel relation " +
          "join fetch relation.targetWord targetWord " +
          "where relation.sourceWord.id = :wordId",
        WordRelationModel.class
      )
      .setParameter("wordId", sourceWordId)
      .getResultList()
      .stream()
      .map(this::relatedWord)
      .sorted(Comparator.comparing(RelatedWordEntity::russian, Comparator.nullsFirst(String::compareTo))
        .thenComparing(related -> related.relation().value())
        .thenComparing(RelatedWordEntity::id))
      .toList();
  }

  @Override
  public Map<UUID, List<RelatedWordEntity>> findOutgoingBySourceIds(List<UUID> sourceWordIds) {
    if (sourceWordIds.isEmpty()) {
      return Map.of();
    }

    Map<UUID, List<RelatedWordEntity>> grouped = new HashMap<>();
    entityManager.createQuery(
        "select relation from WordRelationModel relation " +
          "join fetch relation.targetWord targetWord " +
          "where relation.sourceWord.id in :wordIds",
        WordRelationModel.class
      )
      .setParameter("wordIds", sourceWordIds)
      .getResultList()
      .forEach(relation -> grouped
        .computeIfAbsent(relation.getSourceWord().getId(), ignored -> new ArrayList<>())
        .add(relatedWord(relation)));

    Comparator<RelatedWordEntity> relationOrder = Comparator
      .comparing(RelatedWordEntity::russian, Comparator.nullsFirst(String::compareTo))
      .thenComparing(related -> related.relation().value())
      .thenComparing(RelatedWordEntity::id);
    grouped.values().forEach(relatedWords -> relatedWords.sort(relationOrder));
    grouped.replaceAll((sourceId, relatedWords) -> List.copyOf(relatedWords));
    return Map.copyOf(grouped);
  }

  @Override
  @Transactional
  public void deleteOutgoing(UUID sourceWordId, UUID relationId) {
    if (entityManager.find(WordModel.class, sourceWordId) == null) {
      throw new WordNotFound(sourceWordId.toString());
    }

    long deleted = delete("id = ?1 and sourceWord.id = ?2", relationId, sourceWordId);
    if (deleted == 0) {
      throw new WordRelationNotFound(relationId);
    }
  }

  private RelatedWordEntity relatedWord(WordRelationModel relation) {
    WordModel otherWord = relation.getTargetWord();
    return new RelatedWordEntity(
      otherWord.getId(), relation.getId(), otherWord.getExternalId(), otherWord.getRussian(), relation.getRelation()
    );
  }
}
