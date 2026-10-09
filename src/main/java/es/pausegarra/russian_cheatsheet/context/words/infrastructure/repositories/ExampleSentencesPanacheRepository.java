package es.pausegarra.russian_cheatsheet.context.words.infrastructure.repositories;

import es.pausegarra.russian_cheatsheet.common.domain.pagination_and_sorting.PageInfo;
import es.pausegarra.russian_cheatsheet.common.domain.pagination_and_sorting.Paginated;
import es.pausegarra.russian_cheatsheet.context.words.domain.entities.ExampleSentenceEntity;
import es.pausegarra.russian_cheatsheet.context.words.domain.entities.ImportedExampleReferenceEntity;
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

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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
    return save(List.of(sentence)).get(0);
  }

  @Override
  public List<ExampleSentenceEntity> create(List<ExampleSentenceEntity> sentences) {
    Map<UUID, WordModel> linkedWordsById = findLinkedWords(sentences);
    List<ExampleSentenceModel> models = sentences.stream()
      .map(sentence -> ExampleSentenceModel.fromEntity(sentence, linkedWordsFor(sentence, linkedWordsById)))
      .toList();
    models.forEach(entityManager::persist);
    return models.stream().map(ExampleSentenceModel::toEntity).toList();
  }

  @Override
  public List<ExampleSentenceEntity> save(List<ExampleSentenceEntity> sentences) {
    Map<UUID, WordModel> linkedWordsById = findLinkedWords(sentences);
    List<ExampleSentenceModel> models = sentences.stream()
      .map(sentence -> ExampleSentenceModel.fromEntity(sentence, linkedWordsFor(sentence, linkedWordsById)))
      .toList();
    List<ExampleSentenceModel> saved = models.stream().map(entityManager::merge).toList();
    return saved.stream().map(ExampleSentenceModel::toEntity).toList();
  }

  @Override
  public List<ExampleSentenceEntity> findAllByIds(List<UUID> ids) {
    if (ids.isEmpty()) {
      return List.of();
    }
    return find("id in ?1", ids).list().stream().map(ExampleSentenceModel::toEntity).toList();
  }

  @Override
  public void deleteAllByIds(List<UUID> ids) {
    if (ids.isEmpty()) {
      return;
    }
    find("id in ?1", ids).list().forEach(this::delete);
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
  public Paginated<ImportedExampleReferenceEntity> findImportIndex(int page, int perPage) {
    Page pagination = Page.of(page, perPage);
    long total = count("externalId is not null");
    int offset = pagination.index * pagination.size;
    List<ImportedExampleReferenceEntity> data = entityManager.createQuery(
        "select new es.pausegarra.russian_cheatsheet.context.words.domain.entities.ImportedExampleReferenceEntity(" +
          "sentence.id, sentence.externalId, sentence.checksum) " +
          "from ExampleSentenceModel sentence where sentence.externalId is not null " +
          "order by sentence.externalId, sentence.id",
        ImportedExampleReferenceEntity.class
      )
      .setFirstResult(offset)
      .setMaxResults(pagination.size)
      .getResultList();
    int totalPages = (int) Math.ceil((double) total / pagination.size);
    PageInfo pageInfo = new PageInfo(
      page,
      perPage,
      totalPages,
      total,
      offset + pagination.size < total,
      page > 0
    );

    return new Paginated<>(
      data,
      pageInfo.page(),
      pageInfo.pageSize(),
      pageInfo.totalPages(),
      pageInfo.totalElements(),
      pageInfo.hasNextPage(),
      pageInfo.hasPreviousPage()
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

  private Map<UUID, WordModel> findLinkedWords(List<ExampleSentenceEntity> sentences) {
    List<UUID> wordIds = sentences.stream()
      .flatMap(sentence -> sentence.linkedWordIds().stream())
      .distinct()
      .toList();
    if (wordIds.isEmpty()) {
      return Map.of();
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

    Map<UUID, WordModel> wordsById = new HashMap<>();
    words.forEach(word -> wordsById.put(word.getId(), word));
    return wordsById;
  }

  private List<WordModel> linkedWordsFor(
    ExampleSentenceEntity sentence,
    Map<UUID, WordModel> linkedWordsById
  ) {
    return sentence.linkedWordIds().stream().map(linkedWordsById::get).toList();
  }

}
