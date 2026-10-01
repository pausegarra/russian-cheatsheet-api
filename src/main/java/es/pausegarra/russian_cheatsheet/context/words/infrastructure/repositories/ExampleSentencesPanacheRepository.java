package es.pausegarra.russian_cheatsheet.context.words.infrastructure.repositories;

import es.pausegarra.russian_cheatsheet.common.domain.pagination_and_sorting.PageInfo;
import es.pausegarra.russian_cheatsheet.common.domain.pagination_and_sorting.Paginated;
import es.pausegarra.russian_cheatsheet.context.words.domain.entities.ExternalChecksumEntity;
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
  public Optional<ExampleSentenceEntity> findByExternalId(String externalId) {
    return find("externalId", externalId).firstResultOptional().map(ExampleSentenceModel::toEntity);
  }

  @Override
  public ExampleSentenceEntity save(ExampleSentenceEntity sentence) {
    List<WordModel> words = findLinkedWords(sentence.linkedWordExternalIds());
    ExampleSentenceModel model = ExampleSentenceModel.fromEntity(sentence, words);
    return getEntityManager().merge(model).toEntity();
  }

  @Override
  public Paginated<ExampleSentenceEntity> findAll(int page, int perPage) {
    PanacheQuery<ExampleSentenceModel> query = findAll(Sort.by("externalId")).page(Page.of(page, perPage));
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
      "select distinct sentence from ExampleSentenceModel sentence join sentence.linkedWords word where word.id = ?1",
      Sort.by("externalId"),
      wordId
    ).page(Page.of(page, perPage));
    PageInfo pageInfo = PageInfo.fromQuery(query);
    List<ExampleSentenceEntity> data = query.list().stream().map(ExampleSentenceModel::toEntity).toList();

    return new Paginated<>(
      data, pageInfo.page(), pageInfo.pageSize(), pageInfo.totalPages(), pageInfo.totalElements(),
      pageInfo.hasNextPage(), pageInfo.hasPreviousPage()
    );
  }

  @Override
  public Paginated<ExternalChecksumEntity> findImportChecksums(int page, int perPage) {
    Page.of(page, perPage);
    long total = ((Number) entityManager.createNativeQuery(
      "select count(*) from example_sentences where external_id is not null and checksum is not null"
    ).getSingleResult()).longValue();
    List<?> rows = entityManager.createNativeQuery(
        "select external_id, checksum from example_sentences where external_id is not null and checksum is not null order by external_id"
      )
      .setFirstResult(page * perPage)
      .setMaxResults(perPage)
      .getResultList();
    List<ExternalChecksumEntity> data = rows.stream()
      .map(row -> (Object[]) row)
      .map(columns -> new ExternalChecksumEntity((String) columns[0], (String) columns[1]))
      .toList();
    int totalPages = (int) Math.ceil((double) total / perPage);

    return new Paginated<>(
      data, page, perPage, totalPages, total, page + 1 < totalPages, page > 0
    );
  }

  private List<WordModel> findLinkedWords(List<String> externalIds) {
    if (externalIds.isEmpty()) {
      return List.of();
    }

    List<WordModel> words = entityManager.createQuery(
        "select word from WordModel word where word.externalId in :externalIds", WordModel.class
      )
      .setParameter("externalIds", externalIds)
      .getResultList();
    Set<String> foundIds = new HashSet<>();
    words.forEach(word -> foundIds.add(word.getExternalId()));
    externalIds.stream()
      .filter(externalId -> !foundIds.contains(externalId))
      .findFirst()
      .ifPresent(externalId -> {
        throw new WordNotFound(externalId);
      });

    return words;
  }

}
