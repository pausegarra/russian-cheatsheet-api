package es.pausegarra.russian_cheatsheet.context.words.infrastructure.repositories;

import es.pausegarra.russian_cheatsheet.common.domain.pagination_and_sorting.PageInfo;
import es.pausegarra.russian_cheatsheet.common.domain.pagination_and_sorting.Paginated;
import es.pausegarra.russian_cheatsheet.context.words.domain.entities.ExampleSentenceEntity;
import es.pausegarra.russian_cheatsheet.context.words.domain.exception.WordNotFound;
import es.pausegarra.russian_cheatsheet.context.words.domain.repositories.ExampleSentencesRepository;
import es.pausegarra.russian_cheatsheet.context.words.infrastructure.models.ExampleSentenceModel;
import es.pausegarra.russian_cheatsheet.context.words.infrastructure.models.WordModel;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@ApplicationScoped
@RequiredArgsConstructor
public class ExampleSentencesPanacheRepository implements ExampleSentencesRepository, PanacheRepository<ExampleSentenceModel> {

  private final EntityManager entityManager;

  @Override
  public Optional<ExampleSentenceEntity> findById(UUID id) {
    return find("id", id).firstResultOptional().map(ExampleSentenceModel::toEntity);
  }

  @Override
  public ExampleSentenceEntity save(ExampleSentenceEntity sentence) {
    List<WordModel> words = findLinkedWords(sentence.linkedWordIds());
    ExampleSentenceModel model = ExampleSentenceModel.fromEntity(sentence, words);
    return getEntityManager().merge(model).toEntity();
  }

  @Override
  public Paginated<ExampleSentenceEntity> findAll(int page, int perPage) {
    PanacheQuery<ExampleSentenceModel> query = findAll(Sort.by("id")).page(Page.of(page, perPage));
    PageInfo pageInfo = PageInfo.fromQuery(query);
    List<ExampleSentenceEntity> data = query.list().stream().map(ExampleSentenceModel::toEntity).toList();

    return new Paginated<>(
      data, pageInfo.page(), pageInfo.pageSize(), pageInfo.totalPages(), pageInfo.totalElements(),
      pageInfo.hasNextPage(), pageInfo.hasPreviousPage()
    );
  }

  @Override
  public Paginated<ExampleSentenceEntity> findByWordId(UUID wordId, int page, int perPage) {
    PanacheQuery<ExampleSentenceModel> query = find(
      "select distinct sentence from ExampleSentenceModel sentence join sentence.linkedWords word " +
        "where word.id = ?1 order by sentence.id",
      wordId
    ).page(Page.of(page, perPage));
    PageInfo pageInfo = PageInfo.fromQuery(query);
    List<ExampleSentenceEntity> data = query.list().stream().map(ExampleSentenceModel::toEntity).toList();

    return new Paginated<>(
      data, pageInfo.page(), pageInfo.pageSize(), pageInfo.totalPages(), pageInfo.totalElements(),
      pageInfo.hasNextPage(), pageInfo.hasPreviousPage()
    );
  }

  private List<WordModel> findLinkedWords(List<UUID> wordIds) {
    if (wordIds.isEmpty()) {
      return List.of();
    }

    List<WordModel> words = entityManager.createQuery(
        "select word from WordModel word where word.id in :wordIds", WordModel.class
      )
      .setParameter("wordIds", wordIds)
      .getResultList();
    Set<UUID> foundIds = new HashSet<>();
    words.forEach(word -> foundIds.add(word.getId()));
    wordIds.stream()
      .filter(wordId -> !foundIds.contains(wordId))
      .findFirst()
      .ifPresent(wordId -> {
        throw new WordNotFound(wordId.toString());
      });

    return words;
  }

}
