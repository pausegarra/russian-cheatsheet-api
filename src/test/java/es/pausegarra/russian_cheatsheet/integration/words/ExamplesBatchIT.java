package es.pausegarra.russian_cheatsheet.integration.words;

import es.pausegarra.russian_cheatsheet.base.IntegrationTest;
import es.pausegarra.russian_cheatsheet.context.words.application.dto.ExampleTranslationInputDto;
import es.pausegarra.russian_cheatsheet.context.words.application.use_cases.create_example.CreateExampleDto;
import es.pausegarra.russian_cheatsheet.context.words.infrastructure.models.ExampleSentenceModel;
import es.pausegarra.russian_cheatsheet.context.words.infrastructure.models.WordModel;
import es.pausegarra.russian_cheatsheet.mother.WordMother;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.TestProfile;
import io.quarkus.test.security.TestSecurity;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@QuarkusTest
@TestProfile(BatchLimitProfile.class)
class ExamplesBatchIT extends IntegrationTest {

  @Test
  @TestSecurity(user = "writer", roles = "examples#create")
  void shouldCreateExamplesInInputOrderAndResolveLinkedWords() {
    WordModel word = persist(WordModel.fromEntity(WordMother.random().build()));
    String requestBody = """
      [
        {"russian":"первый","translations":[{"language":"es","text":"primero"}],"linkedWordIds":["%s"]},
        {"russian":"второй","translations":[],"linkedWordIds":[]}
      ]
      """.formatted(word.getId());

    given()
      .contentType("application/json")
      .body(requestBody)
      .when()
      .post("/examples/batch")
      .then()
      .statusCode(201)
      .body("size()", equalTo(2))
      .body("[0].russian", equalTo("первый"))
      .body("[0].linkedWordIds[0]", equalTo(word.getId().toString()))
      .body("[1].russian", equalTo("второй"));
  }

  @Test
  @TestSecurity(user = "writer", roles = {"examples#create", "examples#update"})
  void shouldUpdateExamplesByUuidInInputOrder() {
    String firstId = createExample("original primero");
    String secondId = createExample("original segundo");
    String requestBody = """
      [
        {"id":"%s","russian":"actualizado primero","translations":[],"linkedWordIds":[]},
        {"id":"%s","russian":"actualizado segundo","translations":[],"linkedWordIds":[]}
      ]
      """.formatted(firstId, secondId);

    given()
      .contentType("application/json")
      .body(requestBody)
      .when()
      .put("/examples/batch")
      .then()
      .statusCode(200)
      .body("size()", equalTo(2))
      .body("[0].id", equalTo(firstId))
      .body("[0].russian", equalTo("actualizado primero"))
      .body("[1].id", equalTo(secondId))
      .body("[1].russian", equalTo("actualizado segundo"));
  }

  @Test
  @TestSecurity(user = "writer", roles = "examples#create")
  void shouldRejectEmptyAndOversizedCreateBatchesBeforeWriting() {
    given()
      .contentType("application/json")
      .body("[]")
      .when()
      .post("/examples/batch")
      .then()
      .statusCode(400);

    given()
      .contentType("application/json")
      .body("""
        [
          {"russian":"uno","translations":[]},
          {"russian":"dos","translations":[]},
          {"russian":"tres","translations":[]}
        ]
        """)
      .when()
      .post("/examples/batch")
      .then()
      .statusCode(400);

    Long count = em.createQuery("select count(sentence) from ExampleSentenceModel sentence", Long.class)
      .getSingleResult();
    assertEquals(0, count);
  }

  @Test
  @TestSecurity(user = "writer", roles = "examples#update")
  void shouldRejectEmptyAndOversizedUpdateBatchesBeforeLoadingExamples() {
    given()
      .contentType("application/json")
      .body("[]")
      .when()
      .put("/examples/batch")
      .then()
      .statusCode(400);

    given()
      .contentType("application/json")
      .body("""
        [
          {"id":"%s","russian":"uno"},
          {"id":"%s","russian":"dos"},
          {"id":"%s","russian":"tres"}
        ]
        """.formatted(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID()))
      .when()
      .put("/examples/batch")
      .then()
      .statusCode(400);
  }

  @Test
  @TestSecurity(user = "writer", roles = "examples#create")
  void shouldRollbackCreateBatchWhenLinkedWordIsMissing() {
    UUID missingWordId = UUID.randomUUID();
    String requestBody = """
      [
        {"russian":"válida","translations":[],"linkedWordIds":[]},
        {"russian":"inválida","translations":[],"linkedWordIds":["%s"]}
      ]
      """.formatted(missingWordId);

    given()
      .contentType("application/json")
      .body(requestBody)
      .when()
      .post("/examples/batch")
      .then()
      .statusCode(404);

    Long count = em.createQuery("select count(sentence) from ExampleSentenceModel sentence", Long.class)
      .getSingleResult();
    assertEquals(0, count);
  }

  @Test
  @TestSecurity(user = "writer", roles = {"examples#create", "examples#update"})
  void shouldRollbackUpdateBatchWhenLinkedWordIsMissing() {
    String firstId = createExample("original primero");
    String secondId = createExample("original segundo");
    String requestBody = """
      [
        {"id":"%s","russian":"cambio válido","translations":[],"linkedWordIds":[]},
        {"id":"%s","russian":"cambio inválido","translations":[],"linkedWordIds":["%s"]}
      ]
      """.formatted(firstId, secondId, UUID.randomUUID());

    given()
      .contentType("application/json")
      .body(requestBody)
      .when()
      .put("/examples/batch")
      .then()
      .statusCode(404);

    ExampleSentenceModel first = em.find(ExampleSentenceModel.class, UUID.fromString(firstId));
    assertEquals("original primero", first.getRussian());
  }

  @Test
  @TestSecurity(user = "writer", roles = {"examples#create", "examples#update"})
  void shouldRollbackUpdateBatchWhenAnExampleIdIsMissing() {
    String existingId = createExample("original");
    UUID missingId = UUID.randomUUID();

    given()
      .contentType("application/json")
      .body("""
        [
          {"id":"%s","russian":"cambio válido","translations":[],"linkedWordIds":[]},
          {"id":"%s","russian":"no existe","translations":[],"linkedWordIds":[]}
        ]
        """.formatted(existingId, missingId))
      .when()
      .put("/examples/batch")
      .then()
      .statusCode(404);

    ExampleSentenceModel existing = em.find(ExampleSentenceModel.class, UUID.fromString(existingId));
    assertEquals("original", existing.getRussian());
  }

  @Test
  @TestSecurity(user = "writer")
  void shouldRequireCreateAndUpdateRoles() {
    given()
      .contentType("application/json")
      .body("[{\"russian\":\"слово\",\"translations\":[]}]")
      .when()
      .post("/examples/batch")
      .then()
      .statusCode(403);

    given()
      .contentType("application/json")
      .body("[{\"id\":\"%s\",\"russian\":\"слово\",\"translations\":[]}]"
        .formatted(UUID.randomUUID()))
      .when()
      .put("/examples/batch")
      .then()
      .statusCode(403);
  }

  @Test
  void shouldRequireAuthentication() {
    given()
      .contentType("application/json")
      .body("[{\"russian\":\"слово\",\"translations\":[]}]")
      .when()
      .post("/examples/batch")
      .then()
      .statusCode(401);
  }

  private String createExample(String russian) {
    CreateExampleDto dto = new CreateExampleDto(
      null, russian, List.of(new ExampleTranslationInputDto("es", "traducción", 0)), null, null, List.of()
    );
    var response = given()
      .contentType("application/json")
      .body(dto)
      .when()
      .post("/examples");
    response.then().statusCode(201);
    String id = response.jsonPath().getString("id");
    assertNotNull(UUID.fromString(id));
    return id;
  }
}
