package es.pausegarra.russian_cheatsheet.integration.words;

import es.pausegarra.russian_cheatsheet.base.IntegrationTest;
import es.pausegarra.russian_cheatsheet.context.words.domain.enums.WordType;
import es.pausegarra.russian_cheatsheet.context.words.infrastructure.models.WordModel;
import es.pausegarra.russian_cheatsheet.context.words.infrastructure.models.WordRelationModel;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@QuarkusTest
class WordRelationsIT extends IntegrationTest {

  @Test
  @TestSecurity(user = "importer", roles = "words#create")
  void shouldCreateAndListOnlyOutgoingRelation() {
    WordModel source = importedWord("source");
    WordModel target = importedWord("target");

    Response created = createRelation(source.getId(), target.getId());

    created.then()
      .statusCode(201)
      .body("relatedWordId", equalTo(target.getId().toString()))
      .body("relation", equalTo("related"));
    String relationId = created.then().extract().path("id");
    assertNotNull(UUID.fromString(relationId));

    given().when().get("/api/v1/words/" + source.getId() + "/relations")
      .then().statusCode(200).body("", hasSize(1))
      .body("[0].id", equalTo(relationId));
    given().when().get("/api/v1/words/" + target.getId() + "/relations")
      .then().statusCode(200).body("", hasSize(0));
    given().when().get("/api/v1/words/" + source.getId())
      .then().statusCode(200).body("relatedWords", hasSize(1))
      .body("relatedWords[0].id", equalTo(target.getId().toString()));

    WordModel savedSource = em.find(WordModel.class, source.getId());
    assertEquals("initial-checksum", savedSource.getChecksum());
  }

  @Test
  @TestSecurity(user = "importer", roles = "words#create")
  void shouldStoreMutualRelationAsTwoDirectedRows() {
    WordModel source = importedWord("source");
    WordModel target = importedWord("target");

    createRelation(source.getId(), target.getId()).then().statusCode(201);
    createRelation(target.getId(), source.getId()).then().statusCode(201);

    assertEquals(2L, em.createQuery("select count(relation) from WordRelationModel relation", Long.class)
      .getSingleResult());
    given().when().get("/api/v1/words/" + source.getId() + "/relations")
      .then().statusCode(200).body("", hasSize(1));
    given().when().get("/api/v1/words/" + target.getId() + "/relations")
      .then().statusCode(200).body("", hasSize(1));
  }

  @Test
  @TestSecurity(user = "importer", roles = {"words#create", "words#delete"})
  void shouldDeleteOnlySelectedDirection() {
    WordModel source = importedWord("source");
    WordModel target = importedWord("target");
    String sourceRelationId = createRelation(source.getId(), target.getId()).then()
      .statusCode(201).extract().path("id");
    createRelation(target.getId(), source.getId()).then().statusCode(201);
    String checksumWithOutgoingRelation = checksum(source.getId());
    assertEquals("initial-checksum", checksumWithOutgoingRelation);

    given().when().delete("/api/v1/words/" + source.getId() + "/relations/" + sourceRelationId)
      .then().statusCode(204);

    given().when().get("/api/v1/words/" + source.getId() + "/relations")
      .then().statusCode(200).body("", hasSize(0));
    given().when().get("/api/v1/words/" + target.getId() + "/relations")
      .then().statusCode(200).body("", hasSize(1));
    given().when().get("/api/v1/words/" + source.getId())
      .then().statusCode(200).body("relatedWords", hasSize(0));
    String checksumWithoutOutgoingRelation = checksum(source.getId());
    assertEquals(checksumWithOutgoingRelation, checksumWithoutOutgoingRelation);
    assertEquals("initial-checksum", checksumWithoutOutgoingRelation);
  }

  @Test
  void shouldAllowAnonymousRelationList() {
    WordModel source = importedWord("source");

    given().when().get("/api/v1/words/" + source.getId() + "/relations")
      .then().statusCode(200).body("", hasSize(0));
  }

  @Test
  @TestSecurity(user = "importer", roles = "words#create")
  void shouldReturnExistingRowForDuplicateRelation() {
    WordModel source = importedWord("source");
    WordModel target = importedWord("target");
    String firstRelationId = createRelation(source.getId(), target.getId()).then()
      .statusCode(201).extract().path("id");

    createRelation(source.getId(), target.getId()).then()
      .statusCode(200).body("id", equalTo(firstRelationId));

    assertEquals(1L, em.createQuery("select count(relation) from WordRelationModel relation", Long.class)
      .getSingleResult());
  }

  @Test
  @TestSecurity(user = "importer", roles = "words#create")
  void shouldRejectMissingWordsAndSelfRelations() {
    WordModel source = importedWord("source");
    UUID missingId = UUID.randomUUID();

    createRelation(missingId, source.getId()).then().statusCode(404);
    createRelation(source.getId(), missingId).then().statusCode(404);
    createRelation(source.getId(), source.getId()).then().statusCode(400);
    given().when().get("/api/v1/words/" + missingId + "/relations").then().statusCode(404);
  }

  @Test
  @TestSecurity(user = "reader")
  void shouldRejectCreateWithoutPermission() {
    WordModel source = importedWord("source");
    WordModel target = importedWord("target");

    createRelation(source.getId(), target.getId()).then().statusCode(403);
  }

  @Test
  void shouldRequireAuthenticationForCreate() {
    WordModel source = importedWord("source");
    WordModel target = importedWord("target");

    createRelation(source.getId(), target.getId()).then().statusCode(401);
  }

  @Test
  @TestSecurity(user = "creator", roles = "words#create")
  void shouldRejectDeleteWithoutPermission() {
    WordModel source = importedWord("source");
    WordModel target = importedWord("target");
    String relationId = createRelation(source.getId(), target.getId()).then()
      .statusCode(201).extract().path("id");

    given().when().delete("/api/v1/words/" + source.getId() + "/relations/" + relationId)
      .then().statusCode(403);
  }

  @Test
  @TestSecurity(user = "deleter", roles = {"words#create", "words#delete"})
  void shouldReturnNotFoundWhenDeletingRelationOwnedByAnotherWord() {
    WordModel source = importedWord("source");
    WordModel target = importedWord("target");
    String relationId = createRelation(source.getId(), target.getId()).then()
      .statusCode(201).extract().path("id");

    given().when().delete("/api/v1/words/" + target.getId() + "/relations/" + relationId)
      .then().statusCode(404);
  }

  private Response createRelation(UUID sourceWordId, UUID relatedWordId) {
    return given().contentType("application/json")
      .body("{\"relatedWordId\":\"" + relatedWordId + "\",\"relation\":\"related\"}")
      .when().post("/api/v1/words/" + sourceWordId + "/relations");
  }

  private String checksum(UUID wordId) {
    return (String) em.createNativeQuery("select checksum from words where id = :wordId")
      .setParameter("wordId", wordId)
      .getSingleResult();
  }

  private WordModel importedWord(String suffix) {
    WordModel word = WordModel.builder()
      .id(UUID.randomUUID())
      .externalId(suffix + "-" + UUID.randomUUID())
      .russian(suffix)
      .checksum("initial-checksum")
      .type(WordType.OTHER)
      .translations(new ArrayList<>())
      .build();
    return persist(word);
  }
}
