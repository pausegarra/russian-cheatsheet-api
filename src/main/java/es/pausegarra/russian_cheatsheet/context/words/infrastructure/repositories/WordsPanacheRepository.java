package es.pausegarra.russian_cheatsheet.context.words.infrastructure.repositories;

import es.pausegarra.russian_cheatsheet.common.domain.pagination_and_sorting.Paginated;
import es.pausegarra.russian_cheatsheet.common.domain.exception.BadRequest;
import es.pausegarra.russian_cheatsheet.context.words.domain.criterias.WordSearchCriteria;
import es.pausegarra.russian_cheatsheet.context.words.domain.entities.WordEntity;
import es.pausegarra.russian_cheatsheet.context.words.domain.entities.WordSearchResultEntity;
import es.pausegarra.russian_cheatsheet.context.words.domain.services.WordSearchMatcher;
import es.pausegarra.russian_cheatsheet.context.words.domain.repositories.WordsRepository;
import es.pausegarra.russian_cheatsheet.context.words.infrastructure.models.WordModel;
import es.pausegarra.russian_cheatsheet.common.domain.pagination_and_sorting.SortDirection;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Page;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.LockModeType;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@ApplicationScoped
public class WordsPanacheRepository implements WordsRepository, PanacheRepository<WordModel> {

  private static final Map<String, String> SORT_COLUMNS = Map.ofEntries(
    Map.entry("id", "id"),
    Map.entry("externalId", "external_id"),
    Map.entry("russian", "russian"),
    Map.entry("russianAccented", "russian_accented"),
    Map.entry("usage", "usage"),
    Map.entry("audioUrl", "audio_url"),
    Map.entry("checksum", "checksum"),
    Map.entry("type", "type"),
    Map.entry("aspect", "aspect"),
    Map.entry("forms", "forms"),
    Map.entry("publishedAt", "published_at"),
    Map.entry("createdAt", "created_at"),
    Map.entry("updatedAt", "updated_at"),
    Map.entry("createdBy", "created_by"),
    Map.entry("updatedBy", "updated_by")
  );

  private static final WordSearchMatcher SEARCH_MATCHER = new WordSearchMatcher();

  @Override
  public WordEntity create(WordEntity word) {
    WordModel wordModel = WordModel.fromEntity(word);

    persist(wordModel);

    return wordModel.toEntity();
  }

  @Override
  public List<WordEntity> create(List<WordEntity> words) {
    List<WordModel> wordModels = words.stream().map(WordModel::fromEntity).toList();
    wordModels.forEach(model -> getEntityManager().persist(model));
    return wordModels.stream().map(WordModel::toEntity).toList();
  }

  @Override
  public WordEntity save(WordEntity word) {
    WordModel wordModel = WordModel.fromEntity(word);

    WordModel saved = getEntityManager().merge(wordModel);

    return saved.toEntity();
  }

  @Override
  public List<WordEntity> save(List<WordEntity> words) {
    List<WordModel> models = words.stream().map(WordModel::fromEntity).toList();

    List<WordModel> saved = models.stream().map(getEntityManager()::merge).toList();

    return saved.stream().map(WordModel::toEntity).toList();
  }

  @Override
  public Optional<WordEntity> findById(UUID id) {
    return find("id", id).firstResultOptional().map(WordModel::toEntity);
  }

  @Override
  public List<WordEntity> findAllByIds(List<UUID> ids) {
    if (ids.isEmpty()) {
      return List.of();
    }
    return find("id in ?1", ids).list().stream().map(WordModel::toEntity).toList();
  }

  @Override
  public List<WordEntity> findAllByIdsForUpdate(List<UUID> ids) {
    if (ids.isEmpty()) {
      return List.of();
    }
    return find("id in ?1 order by id", ids)
      .withLock(LockModeType.PESSIMISTIC_WRITE)
      .list()
      .stream()
      .map(WordModel::toEntity)
      .toList();
  }


  @Override
  public void delete(WordEntity word) {
    find("id", word.id()).firstResultOptional().ifPresent(model -> {
      getEntityManager().createNativeQuery("delete from word_examples where word_id = :wordId")
        .setParameter("wordId", model.getId())
        .executeUpdate();
      delete(model);
    });
  }

  @Override
  public void deleteAllByIds(List<UUID> ids) {
    if (ids.isEmpty()) {
      return;
    }
    find("id in ?1", ids).list().stream()
      .map(WordModel::toEntity)
      .forEach(this::delete);
  }

  @Override
  public List<WordEntity> getAll() {
    return findAll().stream().map(WordModel::toEntity).toList();
  }

  @Override
  public Paginated<WordEntity> findByCriteria(WordSearchCriteria criteria) {
    Page page = Page.of(criteria.getPagination().page(), criteria.getPagination().pageSize());
    String sortColumn = SORT_COLUMNS.get(criteria.getSorting().sortBy());
    if (sortColumn == null) {
      throw new BadRequest("Unsupported word sort field: " + criteria.getSorting().sortBy());
    }
    String sortDirection = switch (criteria.getSorting().sortDirection()) {
      case ASC -> "ASC";
      case DESC -> "DESC";
    };
    String publishedFilter = criteria.isPublished() ? "w.published_at is not null" : "w.published_at is null";
    String where = "(" +
      "lower(w.russian) like :search or exists (" +
      "select 1 from jsonb_array_elements(w.translations) as translation(value) " +
      "where lower(translation.value ->> 'text') like :search" +
      ")) and " + publishedFilter;
    String search = "%" + criteria.getSearch().toLowerCase(Locale.ROOT) + "%";
    long total = ((Number) getEntityManager().createNativeQuery("select count(*) from words w where " + where)
      .setParameter("search", search)
      .getSingleResult()).longValue();
    List<WordModel> models = getEntityManager().createNativeQuery(
        "select w.* from words w where " + where + " order by w." + sortColumn + " " + sortDirection,
        WordModel.class
      )
      .setParameter("search", search)
      .setFirstResult(page.index * page.size)
      .setMaxResults(page.size)
      .getResultList();
    int totalPages = (int) Math.ceil((double) total / page.size);

    return new Paginated<>(
      models.stream().map(WordModel::toEntity).toList(),
      page.index,
      page.size,
      totalPages,
      total,
      page.index + 1 < totalPages,
      page.index > 0
    );
  }

  @Override
  public Paginated<WordSearchResultEntity> findSearchMatchesByCriteria(WordSearchCriteria criteria) {
    Page page = Page.of(criteria.getPagination().page(), criteria.getPagination().pageSize());
    String sortColumn = SORT_COLUMNS.get(criteria.getSorting().sortBy());
    if (sortColumn == null) {
      throw new BadRequest("Unsupported word sort field: " + criteria.getSorting().sortBy());
    }
    String sortDirection = switch (criteria.getSorting().sortDirection()) {
      case ASC -> "ASC";
      case DESC -> "DESC";
    };
    String term = WordSearchMatcher.normalize(criteria.getSearch());
    if (term.isEmpty() || term.codePoints().noneMatch(Character::isLetterOrDigit)) {
      return new Paginated<>(List.of(), page.index, page.size, 0, 0, false, page.index > 0);
    }

    String publishedFilter = criteria.isPublished() ? "w.published_at is not null" : "w.published_at is null";
    String candidatePattern = createCandidatePattern(term);
    String russianText = candidateTextSql("w.russian");
    String translationText = candidateTextSql("translation.value ->> 'text'");
    String where = "(" + russianText + " LIKE :candidate ESCAPE E'\\\\' OR EXISTS (" +
      "SELECT 1 FROM jsonb_array_elements(w.translations) AS translation(value) " +
      "WHERE translation.value ->> 'language' IN ('en', 'es') AND " + translationText + " LIKE :candidate ESCAPE E'\\\\'" +
      ")) AND " + publishedFilter;
    String sortExpression = "russian".equals(criteria.getSorting().sortBy())
      ? candidateTextSql("w.russian")
      : "w." + sortColumn;
    @SuppressWarnings("unchecked")
    List<WordModel> candidates = getEntityManager().createNativeQuery(
        "SELECT w.* FROM words w WHERE " + where + " ORDER BY " + sortExpression + " " + sortDirection + ", w.id",
        WordModel.class
      )
      .setParameter("candidate", candidatePattern)
      .getResultList();

    List<WordSearchResultEntity> matches = new ArrayList<>();
    for (WordModel candidate : candidates) {
      SEARCH_MATCHER.match(candidate.toEntity(), term, criteria.getMode()).ifPresent(matches::add);
    }
    matches.sort(Comparator.comparingInt(result -> matchRank(result.match().kind())));

    int total = matches.size();
    int totalPages = (int) Math.ceil((double) total / page.size);
    long requestedFrom = (long) page.index * page.size;
    int from = (int) Math.min(requestedFrom, total);
    int to = (int) Math.min((long) from + page.size, total);
    List<WordSearchResultEntity> results = List.copyOf(matches.subList(from, to));

    return new Paginated<>(results, page.index, page.size, totalPages, total, page.index + 1 < totalPages, page.index > 0);
  }

  private static String candidateTextSql(String expression) {
    return "lower(normalize(replace(" + expression + ", U&'\\0301', ''), NFC))";
  }

  private static String createCandidatePattern(String term) {
    return "%" + Arrays.stream(term.split(" "))
      .map(WordSearchMatcher::normalize)
      .map(WordsPanacheRepository::escapeLike)
      .collect(Collectors.joining("%")) + "%";
  }

  private static String escapeLike(String value) {
    return value.replace("\\", "\\\\").replace("%", "\\%").replace("_", "\\_");
  }

  private static int matchRank(String kind) {
    return switch (kind) {
      case "russian_exact" -> 1;
      case "translation_exact" -> 2;
      case "russian_phrase" -> 3;
      case "translation_phrase" -> 4;
      case "russian_prefix" -> 5;
      case "substring" -> 6;
      default -> throw new IllegalArgumentException("Unsupported word search match kind: " + kind);
    };
  }

}
