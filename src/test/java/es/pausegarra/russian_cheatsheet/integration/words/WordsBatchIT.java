package es.pausegarra.russian_cheatsheet.integration.words;

import es.pausegarra.russian_cheatsheet.base.IntegrationTest;
import es.pausegarra.russian_cheatsheet.context.words.application.use_cases.create_word.CreateWordDto;
import es.pausegarra.russian_cheatsheet.context.words.application.dto.WordTranslationInputDto;
import es.pausegarra.russian_cheatsheet.context.words.application.WordRelationChecksumService;
import es.pausegarra.russian_cheatsheet.context.words.domain.entities.WordEntity;
import es.pausegarra.russian_cheatsheet.context.words.domain.enums.WordRelationType;
import es.pausegarra.russian_cheatsheet.context.words.domain.enums.WordType;
import es.pausegarra.russian_cheatsheet.context.words.infrastructure.models.WordModel;
import es.pausegarra.russian_cheatsheet.context.words.infrastructure.models.WordRelationModel;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.TestProfile;
import io.quarkus.test.security.TestSecurity;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import org.flywaydb.core.Flyway;

import java.util.List;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@QuarkusTest
@TestProfile(BatchLimitProfile.class)
class WordsBatchIT extends IntegrationTest {

  @Inject
  Flyway flyway;

  @Test
  @TestSecurity(user = "writer", roles = "words#create")
  void shouldCreateWordsInInputOrder() {
    String requestBody = """
      [
        {"russian":"первый","type":"other","translations":[]},
        {"russian":"второй","type":"other","translations":[]}
      ]
      """;

    given()
      .contentType("application/json")
      .body(requestBody)
      .when()
      .post("/api/v1/words/batch")
      .then()
      .statusCode(201)
      .body("size()", equalTo(2))
      .body("[0].russian", equalTo("первый"))
      .body("[1].russian", equalTo("второй"));
  }

  @Test
  @TestSecurity(user = "writer", roles = {"words#create", "words#update"})
  void shouldUpdateWordsByUuidInInputOrder() throws Exception {
    String firstId = createWord("первый");
    String secondId = createWord("второй");
    String requestBody = """
      [
        {"id":"%s","russian":"первый обновлён","type":"other"},
        {"id":"%s","russian":"второй обновлён","type":"other"}
      ]
      """.formatted(firstId, secondId);

    given()
      .contentType("application/json")
      .body(requestBody)
      .when()
      .put("/api/v1/words/batch")
      .then()
      .statusCode(200)
      .body("size()", equalTo(2))
      .body("[0].id", equalTo(firstId))
      .body("[0].russian", equalTo("первый обновлён"))
      .body("[1].id", equalTo(secondId))
      .body("[1].russian", equalTo("второй обновлён"));
  }

  @Test
  @TestSecurity(user = "writer", roles = "words#update")
  void shouldIncludeOutgoingRelationsInBatchUpdateResponse() {
    flyway.clean();
    flyway.migrate();
    WordModel source = persist(WordModel.fromEntity(WordEntity.createImported(
      "source-word", "источник", List.of(), null, null, WordType.NOUN, null, null
    )));
    WordModel target = persist(WordModel.fromEntity(WordEntity.createImported(
      "target-word", "цель", List.of(), null, null, WordType.NOUN, null, null
    )));
    WordRelationModel relation = new WordRelationModel();
    relation.setSourceWord(source);
    relation.setTargetWord(target);
    relation.setSourceRussian("источник");
    relation.setRelatedRussian("цель");
    relation.setRelation(WordRelationType.SYNONYM);
    relation.setChecksum(new WordRelationChecksumService().calculate("источник", "цель", WordRelationType.SYNONYM));
    persist(relation);

    String expectedChecksum = new WordRelationChecksumService().calculate(
      "изменено", "цель", WordRelationType.SYNONYM
    );
    given()
      .contentType("application/json")
      .body("[{\"id\":\"%s\",\"russian\":\"изменено\",\"type\":\"noun\"}]".formatted(source.getId()))
      .when()
      .put("/api/v1/words/batch")
      .then()
      .statusCode(200)
      .body("[0].relatedWords.size()", equalTo(1))
      .body("[0].relatedWords[0].id", equalTo(target.getId().toString()))
      .body("[0].relatedWords[0].relation", equalTo("synonym"))
      .body("[0].relatedWords[0].checksum", equalTo(expectedChecksum));

    String storedChecksum = (String) em.createNativeQuery(
        "select checksum from word_relations where id = :relationId"
      )
      .setParameter("relationId", relation.getId())
      .getSingleResult();
    assertEquals(expectedChecksum, storedChecksum);
  }

  @Test
  @TestSecurity(user = "writer", roles = "words#create")
  void shouldRejectEmptyAndOversizedCreateBatchesBeforeWriting() {
    given()
      .contentType("application/json")
      .body("[]")
      .when()
      .post("/api/v1/words/batch")
      .then()
      .statusCode(400);

    given()
      .contentType("application/json")
      .body("""
        [
          {"russian":"uno","type":"other"},
          {"russian":"dos","type":"other"},
          {"russian":"tres","type":"other"}
        ]
        """)
      .when()
      .post("/api/v1/words/batch")
      .then()
      .statusCode(400);

    Long wordCount = em.createQuery("select count(word) from WordModel word", Long.class).getSingleResult();
    assertEquals(0, wordCount);
  }

  @Test
  @TestSecurity(user = "writer", roles = "words#update")
  void shouldRejectEmptyAndOversizedUpdateBatchesBeforeLoadingWords() {
    given()
      .contentType("application/json")
      .body("[]")
      .when()
      .put("/api/v1/words/batch")
      .then()
      .statusCode(400);

    given()
      .contentType("application/json")
      .body("""
        [
          {"id":"%s","russian":"uno","type":"other"},
          {"id":"%s","russian":"dos","type":"other"},
          {"id":"%s","russian":"tres","type":"other"}
        ]
        """.formatted(UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID()))
      .when()
      .put("/api/v1/words/batch")
      .then()
      .statusCode(400);
  }

  @Test
  @TestSecurity(user = "writer", roles = "words#create")
  void shouldRollbackCreateBatchWhenOneWordIsInvalid() {
    given()
      .contentType("application/json")
      .body("""
        [
          {"russian":"válida","type":"other"},
          {"russian":"inválida","type":"verb","aspect":"imperfective","forms":{"ru_noun_sg_nom":"дом"}}
        ]
        """)
      .when()
      .post("/api/v1/words/batch")
      .then()
      .statusCode(400);

    Long wordCount = em.createQuery("select count(word) from WordModel word", Long.class).getSingleResult();
    assertEquals(0, wordCount);
  }

  @Test
  @TestSecurity(user = "writer", roles = {"words#create", "words#update"})
  void shouldRollbackUpdateBatchWhenOneWordIsInvalid() throws Exception {
    String firstId = createWord("original");
    String secondId = createWord("segundo original");

    given()
      .contentType("application/json")
      .body("""
        [
          {"id":"%s","russian":"cambio válido","type":"other"},
          {"id":"%s","russian":"cambio inválido","type":"verb","aspect":"imperfective","forms":{"ru_noun_sg_nom":"дом"}}
        ]
        """.formatted(firstId, secondId))
      .when()
      .put("/api/v1/words/batch")
      .then()
      .statusCode(400);

    WordModel firstWord = em.find(WordModel.class, UUID.fromString(firstId));
    assertEquals("original", firstWord.getRussian());
  }

  @Test
  @TestSecurity(user = "writer", roles = {"words#create", "words#update"})
  void shouldRollbackUpdateBatchWhenAWordIdIsMissing() throws Exception {
    String existingId = createWord("original");
    UUID missingId = UUID.randomUUID();

    given()
      .contentType("application/json")
      .body("""
        [
          {"id":"%s","russian":"cambio válido","type":"other"},
          {"id":"%s","russian":"no existe","type":"other"}
        ]
        """.formatted(existingId, missingId))
      .when()
      .put("/api/v1/words/batch")
      .then()
      .statusCode(404);

    WordModel existing = em.find(WordModel.class, UUID.fromString(existingId));
    assertEquals("original", existing.getRussian());
  }

  @Test
  @TestSecurity(user = "writer")
  void shouldRequireCreateRole() {
    given()
      .contentType("application/json")
      .body("[{\"russian\":\"слово\",\"type\":\"other\"}]")
      .when()
      .post("/api/v1/words/batch")
      .then()
      .statusCode(403);
  }

  @Test
  void shouldRequireAuthentication() {
    given()
      .contentType("application/json")
      .body("[{\"russian\":\"слово\",\"type\":\"other\"}]")
      .when()
      .post("/api/v1/words/batch")
      .then()
      .statusCode(401);
  }

  private String createWord(String russian) throws Exception {
    CreateWordDto dto = new CreateWordDto(
      russian, null, List.of(new WordTranslationInputDto("es", "traducción", 0)),
      null, null, WordType.OTHER, null, null
    );
    var response = given()
      .contentType("application/json")
      .body(objectMapper.writeValueAsString(dto))
      .when()
      .post("/api/v1/words");
    response.then().statusCode(201);
    String id = response.jsonPath().getString("id");
    assertNotNull(UUID.fromString(id));
    return id;
  }
}
