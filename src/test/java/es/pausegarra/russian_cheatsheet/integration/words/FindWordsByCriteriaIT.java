package es.pausegarra.russian_cheatsheet.integration.words;

import es.pausegarra.russian_cheatsheet.base.IntegrationTest;
import es.pausegarra.russian_cheatsheet.context.words.domain.entities.WordEntity;
import es.pausegarra.russian_cheatsheet.context.words.domain.entities.WordTranslationEntity;
import es.pausegarra.russian_cheatsheet.context.words.domain.enums.TranslationOrigin;
import es.pausegarra.russian_cheatsheet.context.words.infrastructure.models.WordModel;
import es.pausegarra.russian_cheatsheet.mother.WordMother;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.not;

@QuarkusTest
public class FindWordsByCriteriaIT extends IntegrationTest {

  @Test
  public void shouldFindWordsByCriteriaWithDefaultValues() {
    WordEntity word = WordMother.random().id(null).build();
    WordModel saved = persist(WordModel.fromEntity(word));

    given().basePath("").when().get("/api/v1/words").then().statusCode(200).body("data.size()", is(1)).body("data[0].id", is(saved.getId().toString()));
  }

  @Test
  public void shouldChangePaginationParameters() {
    List<WordEntity> words = List.of(WordMother.random().id(null).build(), WordMother.random().id(null).build());
    persist(WordModel.fromEntity(words.getFirst()));
    persist(WordModel.fromEntity(words.getLast()));

    given().basePath("").when()
      .get("/api/v1/words?page=1&perPage=1")
      .then()
      .statusCode(200)
      .body("data.size()", is(1))
      .body("page", is(1))
      .body("pageSize", is(1))
      .body("totalPages", is(2))
      .body("totalElements", is(2))
      .body("hasNextPage", is(false))
      .body("hasPreviousPage", is(true));
  }

  @Test
  public void shouldSearch() {
    List<WordEntity> words = List.of(
      WordMother.random().id(null).russian("search").build(),
      WordMother.random().id(null).russian("hidden").build()
    );
    persist(WordModel.fromEntity(words.getFirst()));
    persist(WordModel.fromEntity(words.getLast()));

    given().basePath("").when().get("/api/v1/words?search=search").then().statusCode(200).body("data.size()", is(1)).body("data[0].russian", is("search"));
  }

  @Test
  public void shouldSearchByTranslationText() {
    WordEntity matching = WordMother.random()
      .russian("matching")
      .translations(List.of(new WordTranslationEntity("en", "unique-translation-term", TranslationOrigin.MANUAL, 0)))
      .build();
    WordEntity notMatching = WordMother.random()
      .russian("not-matching")
      .translations(List.of(new WordTranslationEntity("en", "different translation", TranslationOrigin.MANUAL, 0)))
      .build();
    persist(WordModel.fromEntity(matching));
    persist(WordModel.fromEntity(notMatching));

    given().basePath("").when()
      .get("/api/v1/words?search=unique-translation-term")
      .then()
      .statusCode(200)
      .body("data.size()", is(1))
      .body("data[0].russian", is("matching"));
  }

  @Test
  public void shouldMatchWholeWordsWhenPreciseModeIsRequested() {
    persist(WordModel.fromEntity(WordMother.random().id(null).russian("привет").build()));
    persist(WordModel.fromEntity(WordMother.random().id(null).russian("приветик").build()));
    persist(WordModel.fromEntity(WordMother.random().id(null).russian("неприветливый").build()));

    given().basePath("").when()
      .get("/api/v1/words?search=привет&matchMode=precise")
      .then()
      .statusCode(200)
      .body("totalElements", is(1))
      .body("data.size()", is(1))
      .body("data[0].russian", is("привет"))
      .body("data[0].searchMatch.kind", is("russian_exact"))
      .body("data[0].searchMatch.language", is("ru"))
      .body("data[0].searchMatch.text", is("привет"));
  }

  @Test
  public void shouldIncludePartialMatchesWhenContainsModeIsRequested() {
    persist(WordModel.fromEntity(WordMother.random().id(null).russian("привет").build()));
    persist(WordModel.fromEntity(WordMother.random().id(null).russian("приветик").build()));
    persist(WordModel.fromEntity(WordMother.random().id(null).russian("неприветливый").build()));

    given().basePath("").when()
      .get("/api/v1/words?search=привет&matchMode=contains")
      .then()
      .statusCode(200)
      .body("totalElements", is(3))
      .body("data.size()", is(3))
      .body("data.searchMatch.kind", hasItems("russian_exact", "russian_prefix", "substring"));
  }

  @Test
  public void shouldKeepLegacySubstringSearchWhenModeIsOmitted() {
    persist(WordModel.fromEntity(WordMother.random().id(null).russian("привет").build()));

    given().basePath("").when()
      .get("/api/v1/words?search=ривет")
      .then()
      .statusCode(200)
      .body("totalElements", is(1))
      .body("data[0].russian", is("привет"))
      .body("data[0]", not(hasKey("searchMatch")));
  }

  @Test
  public void shouldMatchSpanishGlossWithoutMatchingEnglishSubstrings() {
    persist(WordModel.fromEntity(WordMother.random()
      .id(null)
      .russian("привет")
      .translations(List.of(
        new WordTranslationEntity("es", "¡salud! ¡hola! ¡ola!", TranslationOrigin.MANUAL, 0),
        new WordTranslationEntity("en", "hello", TranslationOrigin.MANUAL, 0)
      ))
      .build()));
    persist(WordModel.fromEntity(WordMother.random()
      .id(null)
      .russian("абстрактный")
      .translations(List.of(new WordTranslationEntity("en", "scholarship", TranslationOrigin.MANUAL, 0)))
      .build()));

    given().basePath("").when()
      .get("/api/v1/words?search=hola&matchMode=precise")
      .then()
      .statusCode(200)
      .body("totalElements", is(1))
      .body("data[0].russian", is("привет"))
      .body("data[0].searchMatch.kind", is("translation_exact"))
      .body("data[0].searchMatch.language", is("es"));
  }

  @Test
  public void shouldRankDirectGlossBeforeContextualTranslationMention() {
    persist(WordModel.fromEntity(WordMother.random()
      .id(null)
      .russian("здравствуй")
      .translations(List.of(new WordTranslationEntity("en", "hello", TranslationOrigin.MANUAL, 0)))
      .build()));
    persist(WordModel.fromEntity(WordMother.random()
      .id(null)
      .russian("алло")
      .translations(List.of(new WordTranslationEntity("en", "greeting, saying hello", TranslationOrigin.MANUAL, 0)))
      .build()));

    given().basePath("").when()
      .get("/api/v1/words?search=hello&matchMode=precise")
      .then()
      .statusCode(200)
      .body("totalElements", is(2))
      .body("data[0].russian", is("здравствуй"))
      .body("data[0].searchMatch.kind", is("translation_exact"))
      .body("data[1].searchMatch.kind", is("translation_phrase"));
  }

  @Test
  public void shouldIgnoreRussianStressMarkAndCaseInPreciseSearch() {
    persist(WordModel.fromEntity(WordMother.random().id(null).russian("ПРИВЕ́Т").build()));

    given().basePath("").when()
      .get("/api/v1/words?search=привет&matchMode=precise")
      .then()
      .statusCode(200)
      .body("totalElements", is(1))
      .body("data[0].searchMatch.kind", is("russian_exact"));
  }

  @Test
  public void shouldTreatSqlPatternCharactersAsLiteralSearchText() {
    persist(WordModel.fromEntity(WordMother.random().id(null).russian("hello world").build()));

    given().basePath("").when()
      .get("/api/v1/words?search=h%25&matchMode=precise")
      .then()
      .statusCode(200)
      .body("totalElements", is(0))
      .body("data.size()", is(0));

    given().basePath("").when()
      .queryParam("search", "h.*")
      .queryParam("matchMode", "precise")
      .get("/api/v1/words")
      .then()
      .statusCode(200)
      .body("totalElements", is(0));

    given().basePath("").when()
      .queryParam("search", "h[")
      .queryParam("matchMode", "precise")
      .get("/api/v1/words")
      .then()
      .statusCode(200)
      .body("totalElements", is(0));
  }

  @Test
  public void shouldTrimAndCollapseUnicodeWhitespaceInPreciseSearch() {
    persist(WordModel.fromEntity(WordMother.random().id(null).russian("привет").build()));

    given().basePath("").when()
      .queryParam("search", "\u00a0ПРИВЕТ\u00a0")
      .queryParam("matchMode", "precise")
      .get("/api/v1/words")
      .then()
      .statusCode(200)
      .body("totalElements", is(1))
      .body("data[0].russian", is("привет"));
  }

  @Test
  public void shouldRankAllMatchesBeforePaginating() {
    persist(WordModel.fromEntity(WordMother.random().id(null).russian("hi").build()));
    persist(WordModel.fromEntity(WordMother.random()
      .id(null)
      .russian("слово")
      .translations(List.of(new WordTranslationEntity("en", "hi", TranslationOrigin.MANUAL, 0)))
      .build()));
    persist(WordModel.fromEntity(WordMother.random()
      .id(null)
      .russian("другое")
      .translations(List.of(new WordTranslationEntity("en", "highway", TranslationOrigin.MANUAL, 0)))
      .build()));

    given().basePath("").when()
      .get("/api/v1/words?search=hi&matchMode=contains&perPage=1&page=1")
      .then()
      .statusCode(200)
      .body("totalElements", is(3))
      .body("totalPages", is(3))
      .body("data.size()", is(1))
      .body("data[0].russian", is("слово"))
      .body("data[0].searchMatch.kind", is("translation_exact"))
      .body("hasNextPage", is(true))
      .body("hasPreviousPage", is(true));
  }

  @Test
  public void shouldReturnEmptyOutOfRangeSearchPageWithAccurateTotals() {
    persist(WordModel.fromEntity(WordMother.random().id(null).russian("привет").build()));

    given().basePath("").when()
      .get("/api/v1/words?search=привет&matchMode=precise&perPage=1&page=1")
      .then()
      .statusCode(200)
      .body("totalElements", is(1))
      .body("totalPages", is(1))
      .body("data.size()", is(0))
      .body("hasNextPage", is(false))
      .body("hasPreviousPage", is(true));
  }

  @Test
  public void shouldKeepRussianYoAndShortIAsDistinctLetters() {
    persist(WordModel.fromEntity(WordMother.random().id(null).russian("еще").build()));
    persist(WordModel.fromEntity(WordMother.random().id(null).russian("ещё").build()));
    persist(WordModel.fromEntity(WordMother.random().id(null).russian("краи").build()));
    persist(WordModel.fromEntity(WordMother.random().id(null).russian("край").build()));

    given().basePath("").when()
      .get("/api/v1/words?search=еще&matchMode=precise")
      .then()
      .statusCode(200)
      .body("totalElements", is(1))
      .body("data[0].russian", is("еще"));

    given().basePath("").when()
      .get("/api/v1/words?search=краи&matchMode=precise")
      .then()
      .statusCode(200)
      .body("totalElements", is(1))
      .body("data[0].russian", is("краи"));
  }

  @Test
  public void shouldPreserveSpanishEnyeAndAcuteAccent() {
    persist(WordModel.fromEntity(WordMother.random()
      .id(null)
      .russian("год")
      .translations(List.of(new WordTranslationEntity("es", "año", TranslationOrigin.MANUAL, 0)))
      .build()));
    persist(WordModel.fromEntity(WordMother.random()
      .id(null)
      .russian("глагол")
      .translations(List.of(new WordTranslationEntity("es", "ano", TranslationOrigin.MANUAL, 0)))
      .build()));

    given().basePath("").when()
      .queryParam("search", "año")
      .queryParam("matchMode", "precise")
      .get("/api/v1/words")
      .then()
      .statusCode(200)
      .body("totalElements", is(1))
      .body("data[0].russian", is("год"))
      .body("data[0].searchMatch.language", is("es"));

    given().basePath("").when()
      .queryParam("search", "ano")
      .queryParam("matchMode", "precise")
      .get("/api/v1/words")
      .then()
      .statusCode(200)
      .body("totalElements", is(1))
      .body("data[0].russian", is("глагол"));
  }

  @Test
  public void shouldPaginateDuplicateRussianEntriesByStableUuid() {
    WordModel first = persist(WordModel.fromEntity(WordMother.random().id(null).russian("дом").build()));
    WordModel second = persist(WordModel.fromEntity(WordMother.random().id(null).russian("дом").build()));
    List<String> expected = List.of(first.getId().toString(), second.getId().toString()).stream().sorted().toList();

    for (int page = 0; page < expected.size(); page++) {
      given().basePath("").when()
        .get("/api/v1/words?search=дом&matchMode=precise&perPage=1&page=" + page)
        .then()
        .statusCode(200)
        .body("totalElements", is(2))
        .body("data.size()", is(1))
        .body("data[0].russian", is("дом"))
        .body("data[0].id", is(expected.get(page)));
    }
  }

  @Test
  public void shouldRequireMultiwordQueryToStayTogetherInOneGloss() {
    persist(WordModel.fromEntity(WordMother.random()
      .id(null)
      .russian("доброе утро")
      .translations(List.of(new WordTranslationEntity("en", "good morning", TranslationOrigin.MANUAL, 0)))
      .build()));
    persist(WordModel.fromEntity(WordMother.random()
      .id(null)
      .russian("слово")
      .translations(List.of(
        new WordTranslationEntity("en", "good", TranslationOrigin.MANUAL, 0),
        new WordTranslationEntity("es", "morning", TranslationOrigin.MANUAL, 1)
      ))
      .build()));
    persist(WordModel.fromEntity(WordMother.random()
      .id(null)
      .russian("другое слово")
      .translations(List.of(new WordTranslationEntity("en", "morning good", TranslationOrigin.MANUAL, 0)))
      .build()));

    given().basePath("").when()
      .queryParam("search", "good morning")
      .queryParam("matchMode", "precise")
      .get("/api/v1/words")
      .then()
      .statusCode(200)
      .body("totalElements", is(1))
      .body("data[0].russian", is("доброе утро"))
      .body("data[0].searchMatch.language", is("en"));
  }

  @Test
  public void shouldRejectUnsupportedMatchMode() {
    given().basePath("").when()
      .get("/api/v1/words?search=привет&matchMode=typo")
      .then()
      .statusCode(400);
  }

}
