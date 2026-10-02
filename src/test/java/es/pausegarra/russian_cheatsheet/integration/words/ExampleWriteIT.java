package es.pausegarra.russian_cheatsheet.integration.words;

import es.pausegarra.russian_cheatsheet.base.IntegrationTest;
import es.pausegarra.russian_cheatsheet.context.words.infrastructure.models.WordModel;
import es.pausegarra.russian_cheatsheet.mother.WordMother;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@QuarkusTest
public class ExampleWriteIT extends IntegrationTest {

  @Test
  @TestSecurity(user = "writer", roles = "examples#create")
  public void shouldCreateExampleWithApiUuidAndLinkedWordIds() {
    WordModel word = persist(WordModel.fromEntity(WordMother.random().build()));
    String requestBody = """
      {"russian":"дом","translations":[{"language":"ES","text":" casa ","position":null}],"linkedWordIds":["%s"]}
      """.formatted(word.getId());

    Response response = given()
      .contentType("application/json")
      .body(requestBody)
      .when()
      .post("/examples");

    response.then().statusCode(201);
    assertDoesNotThrow(() -> UUID.fromString(response.jsonPath().getString("id")));
    assertNull(response.jsonPath().get("externalId"));
    assertEquals(List.of(word.getId().toString()), response.jsonPath().getList("linkedWordIds"));
    assertEquals("es", response.jsonPath().getString("translations[0].language"));
    assertEquals(" casa ", response.jsonPath().getString("translations[0].text"));
    assertEquals(0, response.jsonPath().getInt("translations[0].position"));
  }

  @Test
  @TestSecurity(user = "writer", roles = {"examples#create", "examples#update"})
  public void shouldUpdateExampleByApiUuidAndClearOmittedLinkedWordIds() {
    WordModel word = persist(WordModel.fromEntity(WordMother.random().build()));
    String exampleId = createExample("antes", "[\"" + word.getId() + "\"]");
    String requestBody = """
      {"russian":"después","translations":[{"language":"es","text":"casa","position":1}]}
      """;

    given()
      .contentType("application/json")
      .body(requestBody)
      .when()
      .put("/examples/" + exampleId)
      .then()
      .statusCode(200)
      .body("id", equalTo(exampleId))
      .body("russian", equalTo("después"))
      .body("linkedWordIds", org.hamcrest.Matchers.empty());
  }

  @Test
  @TestSecurity(user = "writer", roles = "examples#update")
  public void shouldReturn404ForUnknownExampleUuid() {
    String id = UUID.randomUUID().toString();

    given()
      .contentType("application/json")
      .body("{\"russian\":\"дом\",\"translations\":[],\"linkedWordIds\":[]}")
      .when()
      .put("/examples/" + id)
      .then()
      .statusCode(404)
      .body("message", equalTo("Example with id " + id + " not found"))
      .body("code", equalTo("NOT_FOUND"));
  }

  @Test
  @TestSecurity(user = "writer", roles = "examples#update")
  public void shouldRejectInvalidExampleUuid() {
    given()
      .contentType("application/json")
      .body("{\"russian\":\"дом\",\"translations\":[],\"linkedWordIds\":[]}")
      .when()
      .put("/examples/not-a-uuid")
      .then()
      .statusCode(400);
  }

  @Test
  @TestSecurity(user = "writer", roles = "examples#create")
  public void shouldReturn404ForUnknownLinkedWordUuid() {
    String requestBody = """
      {"russian":"дом","translations":[],"linkedWordIds":["%s"]}
      """.formatted(UUID.randomUUID());

    given()
      .contentType("application/json")
      .body(requestBody)
      .when()
      .post("/examples")
      .then()
      .statusCode(404);
  }

  @Test
  @TestSecurity(user = "writer", roles = "examples#create")
  public void shouldRejectTranslationWithoutLanguage() {
    given()
      .contentType("application/json")
      .body("{\"russian\":\"дом\",\"translations\":[{\"language\":\" \",\"text\":\"house\"}]}")
      .when()
      .post("/examples")
      .then()
      .statusCode(400);
  }

  private String createExample(String russian, String linkedWordIds) {
    String requestBody = """
      {"russian":"%s","translations":[{"language":"en","text":"example","position":0}],"linkedWordIds":%s}
      """.formatted(russian, linkedWordIds);
    Response response = given()
      .contentType("application/json")
      .body(requestBody)
      .when()
      .post("/examples");
    response.then().statusCode(201);
    return response.jsonPath().getString("id");
  }
}
