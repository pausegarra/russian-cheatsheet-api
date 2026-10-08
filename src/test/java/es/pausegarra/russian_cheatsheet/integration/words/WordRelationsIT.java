package es.pausegarra.russian_cheatsheet.integration.words;

import es.pausegarra.russian_cheatsheet.base.IntegrationTest;
import es.pausegarra.russian_cheatsheet.context.words.domain.enums.WordType;
import es.pausegarra.russian_cheatsheet.context.words.infrastructure.models.WordModel;
import es.pausegarra.russian_cheatsheet.context.words.infrastructure.models.WordRelationModel;
import es.pausegarra.russian_cheatsheet.context.words.application.WordRelationChecksumService;
import es.pausegarra.russian_cheatsheet.context.words.domain.enums.WordRelationType;
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
  void shouldListAllRelationsInPagesWithFullRelatedWord() {
    WordModel firstSource = importedWord("источник-а");
    WordModel firstTarget = importedWord("цель-а");
    WordModel secondSource = importedWord("источник-б");
    WordModel secondTarget = importedWord("цель-б");
    persistRelation(firstSource, firstTarget);
    persistRelation(secondSource, secondTarget);

    given().when().get("/api/v1/words/relations?page=0&perPage=1")
      .then().statusCode(200)
      .body("page", equalTo(0))
      .body("pageSize", equalTo(1))
      .body("totalPages", equalTo(2))
      .body("totalElements", equalTo(2))
      .body("hasNextPage", equalTo(true))
      .body("hasPreviousPage", equalTo(false))
      .body("data", hasSize(1))
      .body("data[0].sourceWordId", equalTo(firstSource.getId().toString()))
      .body("data[0].relatedWordId", equalTo(firstTarget.getId().toString()))
      .body("data[0].relatedWord.id", equalTo(firstTarget.getId().toString()))
      .body("data[0].relatedWord.russian", equalTo("цель-а"))
      .body("data[0].relatedWord.type", equalTo("other"))
      .body("data[0].relatedWord.translations", hasSize(0))
      .body("data[0].relation", equalTo("related"))
      .body("data[0].checksum", equalTo(
        new WordRelationChecksumService().calculate("источник-а", "цель-а", WordRelationType.RELATED)
      ));

    given().when().get("/api/v1/words/relations?page=1&perPage=1")
      .then().statusCode(200)
      .body("page", equalTo(1))
      .body("hasNextPage", equalTo(false))
      .body("hasPreviousPage", equalTo(true))
      .body("data", hasSize(1))
      .body("data[0].sourceWordId", equalTo(secondSource.getId().toString()))
      .body("data[0].relatedWord.russian", equalTo("цель-б"));
  }

  @Test
  void shouldReturnEmptyPageAndRejectInvalidPagination() {
    given().when().get("/api/v1/words/relations")
      .then().statusCode(200)
      .body("data", hasSize(0))
      .body("totalPages", equalTo(0))
      .body("totalElements", equalTo(0));

    given().when().get("/api/v1/words/relations?page=-1&perPage=1")
      .then().statusCode(400);
  }

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
  void shouldPersistChecksumCalculatedFromRussianValues() {
    WordModel source = importedWord("машина");
    WordModel target = importedWord("автомобиль");
    String expectedChecksum = "88f462a165b9e825ea004b0dad94e5b9f192636819ea68b206fa73cd098af586";

    Response created = createRelation(source.getId(), target.getId());

    created.then()
      .statusCode(201)
      .body("checksum", equalTo(expectedChecksum));
    UUID relationId = UUID.fromString(created.then().extract().path("id"));
    String storedChecksum = (String) em.createNativeQuery(
        "select checksum from word_relations where id = :relationId"
      )
      .setParameter("relationId", relationId)
      .getSingleResult();

    assertEquals(expectedChecksum, storedChecksum);
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

  private void persistRelation(WordModel source, WordModel target) {
    WordRelationModel relation = new WordRelationModel();
    relation.setSourceWord(source);
    relation.setTargetWord(target);
    relation.setRelation(WordRelationType.RELATED);
    relation.setChecksum(new WordRelationChecksumService().calculate(
      source.getRussian(), target.getRussian(), WordRelationType.RELATED
    ));
    persist(relation);
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
