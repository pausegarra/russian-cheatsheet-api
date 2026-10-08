package es.pausegarra.russian_cheatsheet.context.words.infrastructure.repositories;

import es.pausegarra.russian_cheatsheet.common.domain.exception.BadRequest;
import es.pausegarra.russian_cheatsheet.common.domain.pagination_and_sorting.PageInfo;
import es.pausegarra.russian_cheatsheet.common.domain.pagination_and_sorting.Paginated;
import es.pausegarra.russian_cheatsheet.context.words.domain.exception.WordNotFound;
import es.pausegarra.russian_cheatsheet.context.words.domain.exception.WordRelationNotFound;
import es.pausegarra.russian_cheatsheet.context.words.domain.entities.RelatedWordEntity;
import es.pausegarra.russian_cheatsheet.context.words.domain.entities.WordRelationDetailsEntity;
import es.pausegarra.russian_cheatsheet.context.words.domain.entities.WordRelationEntity;
import es.pausegarra.russian_cheatsheet.context.words.domain.entities.WordRelationWriteResult;
import es.pausegarra.russian_cheatsheet.context.words.domain.repositories.WordRelationsRepository;
import es.pausegarra.russian_cheatsheet.context.words.infrastructure.models.WordModel;
import es.pausegarra.russian_cheatsheet.context.words.infrastructure.models.WordRelationModel;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Page;
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
  public WordRelationWriteResult createOutgoing(WordRelationEntity relation, String checksum) {
    if (relation == null || relation.sourceWordId() == null || relation.relatedWordId() == null
      || relation.relation() == null) {
      throw new BadRequest("Relation source, target, and type are required");
    }
    if (checksum == null) {
      throw new BadRequest("Relation checksum is required");
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
        "insert into word_relations (id, source_word_id, target_word_id, relation, source_russian, related_russian, checksum) " +
          "values (:relationId, :sourceWordId, :targetWordId, :relationType, :sourceRussian, :relatedRussian, :checksum) " +
          "on conflict (source_word_id, target_word_id, relation) " +
          "do update set relation = excluded.relation, source_russian = excluded.source_russian, " +
          "related_russian = excluded.related_russian, checksum = excluded.checksum " +
          "returning id"
      )
      .setParameter("relationId", candidateRelationId)
      .setParameter("sourceWordId", sourceWordId)
      .setParameter("targetWordId", relation.relatedWordId())
      .setParameter("relationType", relation.relation().value())
      .setParameter("sourceRussian", sourceWord.getRussian())
      .setParameter("relatedRussian", targetWord.getRussian())
      .setParameter("checksum", checksum)
      .getSingleResult();

    WordRelationModel saved = entityManager.find(WordRelationModel.class, storedRelationId);
    return new WordRelationWriteResult(candidateRelationId.equals(storedRelationId), relatedWord(saved));
  }

  @Override
  @Transactional
  public List<WordRelationWriteResult> createOutgoingBatch(List<WordRelationEntity> relations, List<String> checksums) {
    if (relations == null || relations.isEmpty()) {
      throw new BadRequest("Batch must contain at least one item");
    }
    if (checksums == null || relations.size() != checksums.size()) {
      throw new BadRequest("Every relation in the batch requires a checksum");
    }
    List<WordRelationWriteResult> results = new ArrayList<>(relations.size());
    for (int index = 0; index < relations.size(); index++) {
      results.add(createOutgoing(relations.get(index), checksums.get(index)));
    }
    return List.copyOf(results);
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
  public Paginated<WordRelationDetailsEntity> findAll(int page, int perPage) {
    Page pagination = Page.of(page, perPage);
    long total = count();
    long offset = (long) pagination.index * pagination.size;
    List<WordRelationDetailsEntity> data = offset > Integer.MAX_VALUE
      ? List.of()
      : entityManager.createQuery(
          "select relation from WordRelationModel relation " +
            "join fetch relation.sourceWord sourceWord " +
            "join fetch relation.targetWord targetWord " +
            "order by sourceWord.russian, targetWord.russian, relation.relation, relation.id",
          WordRelationModel.class
        )
        .setFirstResult((int) offset)
        .setMaxResults(pagination.size)
        .getResultList()
        .stream()
        .map(this::details)
        .toList();

    int totalPages = (int) Math.ceil((double) total / pagination.size);
    PageInfo pageInfo = new PageInfo(
      pagination.index,
      pagination.size,
      totalPages,
      total,
      offset + pagination.size < total,
      pagination.index > 0
    );
    return new Paginated<>(
      data, pageInfo.page(), pageInfo.pageSize(), pageInfo.totalPages(), pageInfo.totalElements(),
      pageInfo.hasNextPage(), pageInfo.hasPreviousPage()
    );
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

  @Override
  @Transactional
  public void deleteOutgoingBatch(List<WordRelationEntity> relations) {
    if (relations == null || relations.isEmpty()) {
      throw new BadRequest("Batch must contain at least one item");
    }

    for (WordRelationEntity relation : relations) {
      if (relation == null || relation.sourceWordId() == null || relation.relatedWordId() == null
        || relation.relation() == null) {
        throw new BadRequest("Relation source, target, and type are required");
      }
      if (relation.sourceWordId().equals(relation.relatedWordId())) {
        throw new BadRequest("A word cannot be related to itself");
      }
      if (entityManager.find(WordModel.class, relation.sourceWordId()) == null) {
        throw new WordNotFound(relation.sourceWordId().toString());
      }
      if (entityManager.find(WordModel.class, relation.relatedWordId()) == null) {
        throw new WordNotFound(relation.relatedWordId().toString());
      }

      int deleted = entityManager.createNativeQuery(
          "delete from word_relations " +
            "where source_word_id = :sourceWordId and target_word_id = :targetWordId and relation = :relationType"
        )
        .setParameter("sourceWordId", relation.sourceWordId())
        .setParameter("targetWordId", relation.relatedWordId())
        .setParameter("relationType", relation.relation().value())
        .executeUpdate();
      if (deleted == 0) {
        throw new WordRelationNotFound(relation);
      }
    }
  }

  private RelatedWordEntity relatedWord(WordRelationModel relation) {
    WordModel otherWord = relation.getTargetWord();
    return new RelatedWordEntity(
      otherWord.getId(), relation.getId(), otherWord.getExternalId(), otherWord.getRussian(), relation.getRelation(),
      relation.getChecksum()
    );
  }

  private WordRelationDetailsEntity details(WordRelationModel relation) {
    return new WordRelationDetailsEntity(
      relation.getId(), relation.getSourceWord().getId(), relation.getTargetWord().toEntity(), relation.getRelation(),
      relation.getChecksum()
    );
  }
}
