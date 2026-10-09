package es.pausegarra.russian_cheatsheet.integration.words;

import es.pausegarra.russian_cheatsheet.base.IntegrationTest;
import es.pausegarra.russian_cheatsheet.context.words.domain.entities.ExampleSentenceEntity;
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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@QuarkusTest
@TestProfile(BatchLimitProfile.class)
class DeleteExamplesBatchIT extends IntegrationTest {

  @Test
  @TestSecurity(user = "writer", roles = "examples#delete")
  void shouldDeleteExamplesAndUnlinkWordsAndIgnoreMissingIds() {
    WordModel word = persist(WordModel.fromEntity(WordMother.random().build()));
    ExampleSentenceModel first = persist(ExampleSentenceModel.fromEntity(
      ExampleSentenceEntity.create("первый", List.of(), null, null, List.of(word.getId())), List.of(word)
    ));
    ExampleSentenceModel second = persist(ExampleSentenceModel.fromEntity(
      ExampleSentenceEntity.create("второй", List.of(), null, null, List.of(word.getId())), List.of(word)
    ));

    given()
      .contentType("application/json")
      .body(List.of(first.getId(), second.getId()))
      .when()
      .delete("/api/v1/examples/batch")
      .then()
      .statusCode(204);

    given()
      .contentType("application/json")
      .body(List.of(UUID.randomUUID()))
      .when()
      .delete("/api/v1/examples/batch")
      .then()
      .statusCode(204);

    assertNull(em.find(ExampleSentenceModel.class, first.getId()));
    assertNull(em.find(ExampleSentenceModel.class, second.getId()));
    assertNotNull(em.find(WordModel.class, word.getId()));
    Number links = (Number) em.createNativeQuery(
      "select count(*) from word_examples where word_id = :wordId"
    ).setParameter("wordId", word.getId()).getSingleResult();
    assertEquals(0, links.intValue());
  }

  @Test
  @TestSecurity(user = "writer", roles = "examples#delete")
  void shouldRejectEmptyOversizedAndDuplicateBatchesBeforeDeleting() {
    ExampleSentenceModel example = persist(ExampleSentenceModel.fromEntity(
      ExampleSentenceEntity.create("пример", List.of(), null, null, List.of()), List.of()
    ));

    given()
      .contentType("application/json")
      .body(List.of())
      .when()
      .delete("/api/v1/examples/batch")
      .then()
      .statusCode(400);

    given()
      .contentType("application/json")
      .body(List.of(example.getId(), UUID.randomUUID(), UUID.randomUUID()))
      .when()
      .delete("/api/v1/examples/batch")
      .then()
      .statusCode(400);

    given()
      .contentType("application/json")
      .body(List.of(example.getId(), example.getId()))
      .when()
      .delete("/api/v1/examples/batch")
      .then()
      .statusCode(400);

    assertNotNull(em.find(ExampleSentenceModel.class, example.getId()));
  }

  @Test
  @TestSecurity(user = "writer")
  void shouldRequireDeleteRole() {
    given()
      .contentType("application/json")
      .body(List.of(UUID.randomUUID()))
      .when()
      .delete("/api/v1/examples/batch")
      .then()
      .statusCode(403);
  }

  @Test
  void shouldRequireAuthentication() {
    given()
      .contentType("application/json")
      .body(List.of(UUID.randomUUID()))
      .when()
      .delete("/api/v1/examples/batch")
      .then()
      .statusCode(401);
  }
}
