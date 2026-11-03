package es.pausegarra.russian_cheatsheet.context.words.infrastructure.repositories;

import es.pausegarra.russian_cheatsheet.common.domain.pagination_and_sorting.Paginated;
import es.pausegarra.russian_cheatsheet.common.domain.exception.BadRequest;
import es.pausegarra.russian_cheatsheet.context.words.domain.criterias.WordSearchCriteria;
import es.pausegarra.russian_cheatsheet.context.words.domain.entities.WordEntity;
import es.pausegarra.russian_cheatsheet.context.words.domain.entities.WordSearchResultEntity;
import es.pausegarra.russian_cheatsheet.context.words.domain.enums.WordSearchMode;
import es.pausegarra.russian_cheatsheet.context.words.domain.repositories.WordsRepository;
import es.pausegarra.russian_cheatsheet.context.words.infrastructure.models.WordModel;
import es.pausegarra.russian_cheatsheet.common.domain.pagination_and_sorting.SortDirection;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Page;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.LockModeType;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.text.Normalizer;
import java.util.regex.Pattern;

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

  private static final Pattern WHITESPACE = Pattern.compile("\\s+", Pattern.UNICODE_CHARACTER_CLASS);

  private static final String SEARCH_CTES = """
    WITH term AS (
      SELECT CAST(:term AS text) AS value, CAST(:mode AS text) AS mode
    ), fields AS (
      SELECT w.id AS word_id, 'ru'::text AS language, 0 AS language_rank, 0 AS translation_position,
        w.russian AS match_text, %s AS normalized_text
      FROM words w
      WHERE %s
      UNION ALL
      SELECT w.id AS word_id, translation.value ->> 'language' AS language,
        CASE translation.value ->> 'language' WHEN 'en' THEN 1 ELSE 2 END AS language_rank,
        COALESCE((translation.value ->> 'position')::integer, 0) AS translation_position,
        translation.value ->> 'text' AS match_text, %s AS normalized_text
      FROM words w
      CROSS JOIN LATERAL jsonb_array_elements(w.translations) AS translation(value)
      WHERE %s AND translation.value ->> 'language' IN ('en', 'es')
    ), gloss_segments AS (
      SELECT f.*,
        trim(regexp_replace(regexp_replace(segment.value, '\\([^)]*\\)', '', 'g'), '[[:space:]]+', ' ', 'g'))
          AS normalized_segment
      FROM fields f
      CROSS JOIN LATERAL regexp_split_to_table(f.normalized_text, '[,;!¡?¿]+') AS segment(value)
      WHERE f.language <> 'ru'
    ), matches AS (
      SELECT f.word_id,
        CASE WHEN f.normalized_text = t.value THEN 1
             WHEN f.normalized_text ~ :wordPattern THEN 3
             WHEN t.mode = 'contains' AND strpos(f.normalized_text, t.value) = 1 THEN 5
             ELSE 6 END AS match_rank,
        CASE WHEN f.normalized_text = t.value THEN 'russian_exact'
             WHEN f.normalized_text ~ :wordPattern THEN 'russian_phrase'
             WHEN t.mode = 'contains' AND strpos(f.normalized_text, t.value) = 1 THEN 'russian_prefix'
             ELSE 'substring' END AS kind,
        f.language, f.language_rank, f.translation_position, f.match_text
      FROM fields f CROSS JOIN term t
      WHERE f.language = 'ru'
        AND (f.normalized_text = t.value OR f.normalized_text ~ :wordPattern
             OR (t.mode = 'contains' AND strpos(f.normalized_text, t.value) > 0))
      UNION ALL
      SELECT f.word_id,
        CASE WHEN f.normalized_segment = t.value THEN 2
             WHEN f.normalized_text ~ :wordPattern THEN 4
             ELSE 6 END AS match_rank,
        CASE WHEN f.normalized_segment = t.value THEN 'translation_exact'
             WHEN f.normalized_text ~ :wordPattern THEN 'translation_phrase'
             ELSE 'substring' END AS kind,
        f.language, f.language_rank, f.translation_position, f.match_text
      FROM gloss_segments f CROSS JOIN term t
      WHERE f.normalized_segment = t.value OR f.normalized_text ~ :wordPattern
        OR (t.mode = 'contains' AND strpos(f.normalized_text, t.value) > 0)
    ), best_matches AS (
      SELECT DISTINCT ON (word_id) word_id, match_rank, kind, language, match_text
      FROM matches
      ORDER BY word_id, match_rank, language_rank, translation_position, match_text, language
    )
    """;

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
    String term = normalizeSearch(criteria.getSearch());
    if (term.isEmpty() || term.codePoints().noneMatch(Character::isLetterOrDigit)) {
      return new Paginated<>(List.of(), page.index, page.size, 0, 0, false, page.index > 0);
    }

    String publishedFilter = criteria.isPublished() ? "w.published_at is not null" : "w.published_at is null";
    String normalizedRussian = normalizedSql("w.russian");
    String ctes = SEARCH_CTES.formatted(
      normalizedRussian,
      publishedFilter,
      normalizedSql("translation.value ->> 'text'"),
      publishedFilter
    );
    String pattern = "(^|[^[:alnum:]])" + escapePostgresRegex(term) + "([^[:alnum:]]|$)";
    long total = ((Number) getEntityManager().createNativeQuery(ctes + " SELECT COUNT(*) FROM best_matches")
      .setParameter("term", term)
      .setParameter("mode", criteria.getMode() == WordSearchMode.CONTAINS ? "contains" : "precise")
      .setParameter("wordPattern", pattern)
      .getSingleResult()).longValue();

    if (total == 0) {
      return new Paginated<>(List.of(), page.index, page.size, 0, 0, false, page.index > 0);
    }

    String ordering = "russian".equals(criteria.getSorting().sortBy()) ? normalizedRussian : "w." + sortColumn;
    @SuppressWarnings("unchecked")
    List<Object[]> matchRows = getEntityManager().createNativeQuery(
        ctes + " SELECT best.word_id, best.kind, best.language, best.match_text " +
          "FROM best_matches best JOIN words w ON w.id = best.word_id " +
          "ORDER BY best.match_rank, " + ordering + " " + sortDirection + ", w.id " +
          "LIMIT :limit OFFSET :offset"
      )
      .setParameter("term", term)
      .setParameter("mode", criteria.getMode() == WordSearchMode.CONTAINS ? "contains" : "precise")
      .setParameter("wordPattern", pattern)
      .setParameter("limit", page.size)
      .setParameter("offset", page.index * page.size)
      .getResultList();

    List<UUID> ids = matchRows.stream().map(row -> (UUID) row[0]).toList();
    Map<UUID, WordModel> modelsById = find("id in ?1", ids).list().stream()
      .collect(java.util.stream.Collectors.toMap(WordModel::getId, model -> model));
    List<WordSearchResultEntity> results = matchRows.stream().map(row -> {
      WordEntity word = modelsById.get((UUID) row[0]).toEntity();
      WordSearchResultEntity.Match match = new WordSearchResultEntity.Match(
        (String) row[1], (String) row[2], (String) row[3]
      );
      return new WordSearchResultEntity(word, match);
    }).toList();
    int totalPages = (int) Math.ceil((double) total / page.size);

    return new Paginated<>(results, page.index, page.size, totalPages, total, page.index + 1 < totalPages, page.index > 0);
  }

  private static String normalizedSql(String expression) {
    return "lower(trim(regexp_replace(normalize(regexp_replace(" + expression +
      ", U&'\\0301', '', 'g'), NFC), '[[:space:]]+', ' ', 'g')))";
  }

  private static String normalizeSearch(String value) {
    String withoutStress = value.replace("\u0301", "").toLowerCase(Locale.ROOT);
    return WHITESPACE.matcher(Normalizer.normalize(withoutStress, Normalizer.Form.NFC))
      .replaceAll(" ")
      .strip();
  }

  private static String escapePostgresRegex(String value) {
    StringBuilder escaped = new StringBuilder(value.length());
    for (int index = 0; index < value.length(); index++) {
      char character = value.charAt(index);
      if ("\\.^$|?*+()[]{}".indexOf(character) >= 0) {
        escaped.append('\\');
      }
      escaped.append(character);
    }
    return escaped.toString();
  }

}
