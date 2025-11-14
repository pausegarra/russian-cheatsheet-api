package es.pausegarra.russian_cheatsheet.integration.words;

import com.fasterxml.jackson.core.JsonProcessingException;
import es.pausegarra.russian_cheatsheet.base.IntegrationTest;
import es.pausegarra.russian_cheatsheet.context.words.application.use_cases.create_word.CreateWordConjugationDto;
import es.pausegarra.russian_cheatsheet.context.words.application.use_cases.create_word.CreateWordDto;
import es.pausegarra.russian_cheatsheet.context.words.domain.enums.WordType;
import es.pausegarra.russian_cheatsheet.context.words.infrastructure.models.WordModel;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@QuarkusTest
public class CreateWordIT extends IntegrationTest {

  @Test
  @TestSecurity(
    user = "user",
    roles = "words#create"
  )
  public void shouldCreateWordWithoutChildren() throws JsonProcessingException {
    CreateWordDto createWordDto = new CreateWordDto(
      "russian",
      "english",
      "spanish",
      WordType.OTHER,
      null,
      null,
      null
    );
    String json = objectMapper.writeValueAsString(createWordDto);

    given()
      .contentType("application/json")
      .body(json)
      .when()
      .post("/words")
      .then()
      .statusCode(201)
      .body("russian", equalTo("russian"))
      .body("english", equalTo("english"))
      .body("spanish", equalTo("spanish"))
      .body("type", equalTo("OTHER"))
      .body("createdBy", equalTo("user"));

    WordModel saved = em.createQuery("select w from WordModel w", WordModel.class).getSingleResult();

    assertNotNull(saved);
    assertEquals("russian", saved.getRussian());
    assertEquals("english", saved.getEnglish());
    assertEquals("spanish", saved.getSpanish());
    assertEquals(WordType.OTHER, saved.getType());
  }

  @Test
  @TestSecurity(
    user = "user",
    roles = "words#create"
  )
  public void shouldCreateWordWithConjugationsWhenTypeIsVerb() throws JsonProcessingException {
    CreateWordConjugationDto conjugations = new CreateWordConjugationDto(
      "imperfectivePresentFirstPersonSingular",
      "imperfectivePresentSecondPersonSingular",
      "imperfectivePresentThirdPersonSingular",
      "imperfectivePresentFirstPersonPlural",
      "imperfectivePresentSecondPersonPlural",
      "imperfectivePresentThirdPersonPlural",
      "imperfectivePastMasculine",
      "imperfectivePastFeminine",
      "imperfectivePastNeuter",
      "imperfectivePastPlural",
      "imperfectiveFutureFirstPersonSingular",
      "imperfectiveFutureSecondPersonSingular",
      "imperfectiveFutureThirdPersonSingular",
      "imperfectiveFutureFirstPersonPlural",
      "imperfectiveFutureSecondPersonPlural",
      "imperfectiveFutureThirdPersonPlural",
      "perfectivePastMasculine",
      "perfectivePastFeminine",
      "perfectivePastNeuter",
      "perfectivePastPlural",
      "perfectiveFutureFirstPersonSingular",
      "perfectiveFutureSecondPersonSingular",
      "perfectiveFutureThirdPersonSingular",
      "perfectiveFutureFirstPersonPlural",
      "perfectiveFutureSecondPersonPlural",
      "perfectiveFutureThirdPersonPlural"
    );
    CreateWordDto createWordDto = new CreateWordDto(
      "russian",
      "english",
      "spanish",
      WordType.VERB,
      conjugations,
      null,
      null
    );
    String json = objectMapper.writeValueAsString(createWordDto);

    given()
      .contentType("application/json")
      .body(json)
      .when()
      .post("/words")
      .then()
      .statusCode(201)
      .body("russian", equalTo("russian"))
      .body("english", equalTo("english"))
      .body("spanish", equalTo("spanish"))
      .body("type", equalTo("VERB"))
      .body("conjugations.imperfectivePresentFirstPersonSingular", equalTo("imperfectivePresentFirstPersonSingular"))
      .body("conjugations.imperfectivePresentSecondPersonSingular", equalTo("imperfectivePresentSecondPersonSingular"))
      .body("conjugations.imperfectivePresentThirdPersonSingular", equalTo("imperfectivePresentThirdPersonSingular"))
      .body("conjugations.imperfectivePresentFirstPersonPlural", equalTo("imperfectivePresentFirstPersonPlural"))
      .body("conjugations.imperfectivePresentSecondPersonPlural", equalTo("imperfectivePresentSecondPersonPlural"))
      .body("conjugations.imperfectivePresentThirdPersonPlural", equalTo("imperfectivePresentThirdPersonPlural"))
      .body("conjugations.imperfectivePastMasculine", equalTo("imperfectivePastMasculine"))
      .body("conjugations.imperfectivePastFeminine", equalTo("imperfectivePastFeminine"))
      .body("conjugations.imperfectivePastNeuter", equalTo("imperfectivePastNeuter"))
      .body("conjugations.imperfectivePastPlural", equalTo("imperfectivePastPlural"))
      .body("conjugations.imperfectiveFutureFirstPersonSingular", equalTo("imperfectiveFutureFirstPersonSingular"))
      .body("conjugations.imperfectiveFutureSecondPersonSingular", equalTo("imperfectiveFutureSecondPersonSingular"))
      .body("conjugations.imperfectiveFutureThirdPersonSingular", equalTo("imperfectiveFutureThirdPersonSingular"))
      .body("conjugations.imperfectiveFutureFirstPersonPlural", equalTo("imperfectiveFutureFirstPersonPlural"))
      .body("conjugations.imperfectiveFutureSecondPersonPlural", equalTo("imperfectiveFutureSecondPersonPlural"))
      .body("conjugations.imperfectiveFutureThirdPersonPlural", equalTo("imperfectiveFutureThirdPersonPlural"))
      .body("conjugations.perfectivePastMasculine", equalTo("perfectivePastMasculine"))
      .body("conjugations.perfectivePastFeminine", equalTo("perfectivePastFeminine"))
      .body("conjugations.perfectivePastNeuter", equalTo("perfectivePastNeuter"))
      .body("conjugations.perfectivePastPlural", equalTo("perfectivePastPlural"))
      .body("conjugations.perfectiveFutureFirstPersonSingular", equalTo("perfectiveFutureFirstPersonSingular"))
      .body("conjugations.perfectiveFutureSecondPersonSingular", equalTo("perfectiveFutureSecondPersonSingular"))
      .body("conjugations.perfectiveFutureThirdPersonSingular", equalTo("perfectiveFutureThirdPersonSingular"))
      .body("conjugations.perfectiveFutureFirstPersonPlural", equalTo("perfectiveFutureFirstPersonPlural"))
      .body("conjugations.perfectiveFutureSecondPersonPlural", equalTo("perfectiveFutureSecondPersonPlural"))
      .body("conjugations.perfectiveFutureThirdPersonPlural", equalTo("perfectiveFutureThirdPersonPlural"));

    WordModel saved = em.createQuery("select w from WordModel w", WordModel.class).getSingleResult();

    assertNotNull(saved);
    assertEquals("russian", saved.getRussian());
    assertEquals("english", saved.getEnglish());
    assertEquals("spanish", saved.getSpanish());
    assertEquals(WordType.VERB, saved.getType());
    assertNotNull(saved.getConjugations());
  }

  @Test
  @TestSecurity(
    user = "user",
    roles = "words#create"
  )
  public void shouldReturn400WhenCreateWordWithNoConjugationsAndTypeIsVerb() throws JsonProcessingException {
    CreateWordDto createWordDto = new CreateWordDto(
      "russian",
      "english",
      "spanish",
      WordType.VERB,
      null,
      null,
      null
    );
    String json = objectMapper.writeValueAsString(createWordDto);

    given()
      .contentType("application/json")
      .body(json)
      .when()
      .post("/words")
      .then()
      .statusCode(400)
      .body("code", equalTo("CONJUGATIONS_REQUIRED"));
  }

  @Test
  @TestSecurity(
    user = "user",
    roles = "words#create"
  )
  public void shouldReturn400WhenCreateWordWithNoDeclinationsAndTypeIsNoun() throws JsonProcessingException {
    CreateWordDto createWordDto = new CreateWordDto(
      "russian",
      "english",
      "spanish",
      WordType.NOUN,
      null,
      null,
      null
    );
    String json = objectMapper.writeValueAsString(createWordDto);

    given()
      .contentType("application/json")
      .body(json)
      .when()
      .post("/words")
      .then()
      .statusCode(400)
      .body("code", equalTo("DECLINATIONS_REQUIRED"));
  }

  @Test
  @TestSecurity(
    user = "user",
    roles = "words#create"
  )
  public void shouldReturn400WhenCreateWordWithNoDeclinationMatrixAndTypeIsAdjectiveOrPronounOrParticipleOrOrdinal() throws JsonProcessingException {
    CreateWordDto createWordDto = new CreateWordDto(
      "russian",
      "english",
      "spanish",
      WordType.ADJECTIVE,
      null,
      null,
      null
    );
    String json = objectMapper.writeValueAsString(createWordDto);

    given()
      .contentType("application/json")
      .body(json)
      .when()
      .post("/words")
      .then()
      .statusCode(400)
      .body("code", equalTo("DECLINATIONS_REQUIRED"));
  }

  @Test
  @TestSecurity(
    user = "user"
  )
  public void shouldReturn403WhenUserIsNotAuthorized() {
    given()
      .contentType("application/json")
      .when()
      .post("/words")
      .then()
      .statusCode(403);
  }

  @Test
  public void shouldReturn401WhenUserIsNotAuthenticated() {
    given()
      .contentType("application/json")
      .when()
      .post("/words")
      .then()
      .statusCode(401);
  }

}
