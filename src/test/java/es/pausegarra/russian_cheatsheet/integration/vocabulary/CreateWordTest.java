package es.pausegarra.russian_cheatsheet.integration.vocabulary;

import com.fasterxml.jackson.core.JsonProcessingException;
import es.pausegarra.russian_cheatsheet.annotations.IntegrationTest;
import es.pausegarra.russian_cheatsheet.mother.CreateWordRequestMother;
import es.pausegarra.russian_cheatsheet.mother.WordConjugationRequestMother;
import es.pausegarra.russian_cheatsheet.mother.WordEntityMother;
import es.pausegarra.russian_cheatsheet.vocabulary.domain.entities.WordEntity;
import es.pausegarra.russian_cheatsheet.vocabulary.domain.enums.WordTypes;
import es.pausegarra.russian_cheatsheet.vocabulary.infrastructure.requests.CreateWordRequest;
import es.pausegarra.russian_cheatsheet.vocabulary.infrastructure.requests.WordRequestConjugations;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import io.restassured.response.Response;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@QuarkusTest
public class CreateWordTest extends IntegrationTest {

  @Transactional
  public WordEntity createWord() {
    WordEntity word = WordEntityMother.random()
      .id(null)
      .build();
    em.persist(word);

    em.flush();

    return word;
  }

  @Test
  @TestSecurity(
    user = "test", roles = {"words#create"}
  )
  public void shouldCreateWord() throws JsonProcessingException {
    WordRequestConjugations conjugations = WordConjugationRequestMother.random()
      .build();
    CreateWordRequest request = CreateWordRequestMother.random()
      .type(WordTypes.VERB.toString())
      .conjugations(conjugations)
      .build();
    String json = objectMapper.writeValueAsString(request);

    Response response = given().body(json)
      .contentType("application/json")
      .when()
      .post("/words")
      .then()
      .statusCode(201)
      .body(notNullValue())
      .extract()
      .response();

    WordEntity word = em.find(WordEntity.class,
      UUID.fromString(response.getBody()
        .jsonPath()
        .getString("resourceId"))
    );

    assertEquals(word.getRussian(), request.russian());
    assertEquals(word.getEnglish(), request.english());
    assertEquals(word.getSpanish(), request.spanish());
    assertEquals(word.getType(), WordTypes.valueOf(request.type()));
    assertNotNull(word.getConjugations());
  }

  @Test
  @TestSecurity(
    user = "test", roles = {"words#create"}
  )
  public void shouldReturn400WhenDataIsInvalid() throws JsonProcessingException {
    CreateWordRequest request = CreateWordRequestMother.random()
      .russian(null)
      .english(null)
      .spanish(null)
      .build();
    String json = objectMapper.writeValueAsString(request);

    given().body(json)
      .contentType("application/json")
      .when()
      .post("/words")
      .then()
      .statusCode(400)
      .body("errors.size()", is(3))
      .body(
        "errors.field",
        hasItems("handle.dto.russian", "handle.dto.english", "handle.dto.spanish")
      )
      .body("errors.message", hasItem("must not be blank"));
  }

  @Test
  public void shouldReturn401WhenUserIsNotAuthenticated() throws JsonProcessingException {
    CreateWordRequest request = CreateWordRequestMother.random()
      .build();
    String json = objectMapper.writeValueAsString(request);

    given().body(json)
      .contentType("application/json")
      .when()
      .post("/words")
      .then()
      .statusCode(401);
  }

  @Test
  @TestSecurity(user = "test")
  public void shouldReturn403WhenUserDoesNotHavePermissions() throws JsonProcessingException {
    CreateWordRequest request = CreateWordRequestMother.random()
      .build();
    String json = objectMapper.writeValueAsString(request);

    given().body(json)
      .contentType("application/json")
      .when()
      .post("/words")
      .then()
      .statusCode(403);
  }

  @Test
  @TestSecurity(
    user = "test", roles = {"words#create"}
  )
  public void shouldWotkWithoutConjugations() throws JsonProcessingException {
    CreateWordRequest request = CreateWordRequestMother.random()
      .build();
    String json = objectMapper.writeValueAsString(request);

    given().body(json)
      .contentType("application/json")
      .when()
      .post("/words")
      .then()
      .statusCode(201)
      .body(notNullValue());
  }

  @Test
  @TestSecurity(
    user = "test", roles = {"words#create"}
  )
  public void shouldReturn400WhenWordAlreadyExists() throws JsonProcessingException {
    WordEntity word = createWord();

    CreateWordRequest request = CreateWordRequestMother.random()
      .russian(word.getRussian())
      .build();
    String json = objectMapper.writeValueAsString(request);


    given().body(json)
      .contentType("application/json")
      .when()
      .post("/words")
      .then()
      .statusCode(400)
      .body("code", is("WORD_ALREADY_EXISTS"))
      .body("message", is("Word already exists: " + word.getRussian()))
      .body("status", is(400));
  }

}
