package es.pausegarra.russian_cheatsheet.integration.words;

import es.pausegarra.russian_cheatsheet.base.IntegrationTest;
import es.pausegarra.russian_cheatsheet.context.words.domain.enums.WordRelationType;
import es.pausegarra.russian_cheatsheet.context.words.domain.enums.WordType;
import es.pausegarra.russian_cheatsheet.context.words.infrastructure.models.WordModel;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
class WordRelationsBatchIT extends IntegrationTest {

  @Test
  @TestSecurity(user = "importer", roles = "words#create")
  void shouldCreateRelationsAcrossDifferentSourceWords() {
    WordModel firstSource = importedWord("first-source");
    WordModel secondSource = importedWord("second-source");
    WordModel firstTarget = importedWord("first-target");
    WordModel secondTarget = importedWord("second-target");
    String body = relationArray(
      relationJson(firstSource.getId(), firstTarget.getId(), WordRelationType.RELATED),
      relationJson(secondSource.getId(), secondTarget.getId(), WordRelationType.SYNONYM)
    );

    createBatch(body).then()
      .statusCode(201)
      .body("", hasSize(2))
      .body("[0].wordId", equalTo(firstSource.getId().toString()))
      .body("[0].created", equalTo(true))
      .body("[0].relation.relatedWordId", equalTo(firstTarget.getId().toString()))
      .body("[0].relation.relation", equalTo("related"))
      .body("[1].wordId", equalTo(secondSource.getId().toString()))
      .body("[1].created", equalTo(true))
      .body("[1].relation.relatedWordId", equalTo(secondTarget.getId().toString()))
      .body("[1].relation.relation", equalTo("synonym"));

    given().when().get("/words/" + firstSource.getId() + "/relations")
      .then().statusCode(200).body("", hasSize(1));
    given().when().get("/words/" + secondSource.getId() + "/relations")
      .then().statusCode(200).body("", hasSize(1));
  }

  @Test
  @TestSecurity(user = "importer", roles = "words#create")
  void shouldReturnExistingRelationForDuplicateBatchCreate() {
    WordModel source = importedWord("source");
    WordModel target = importedWord("target");
    String body = relationArray(relationJson(source.getId(), target.getId(), WordRelationType.RELATED));

    String relationId = createBatch(body).then()
      .statusCode(201)
      .extract().path("[0].relation.id");

    createBatch(body).then()
      .statusCode(200)
      .body("", hasSize(1))
      .body("[0].created", equalTo(false))
      .body("[0].relation.id", equalTo(relationId));

    assertEquals(1L, em.createQuery("select count(relation) from WordRelationModel relation", Long.class)
      .getSingleResult());
  }

  @Test
  @TestSecurity(user = "importer", roles = "words#create")
  void shouldRollbackCreateBatchWhenAnyRelationIsInvalid() {
    WordModel source = importedWord("source");
    WordModel importedTarget = importedWord("imported-target");
    WordModel manualTarget = manualWord("manual-target");

    createBatch(relationArray(
      relationJson(source.getId(), importedTarget.getId(), WordRelationType.RELATED),
      relationJson(source.getId(), manualTarget.getId(), WordRelationType.RELATED)
    )).then().statusCode(400);

    given().when().get("/words/" + source.getId() + "/relations")
      .then().statusCode(200).body("", hasSize(0));
  }

  @Test
  @TestSecurity(user = "editor", roles = {"words#create", "words#delete"})
  void shouldDeleteOnlyRequestedDirectionAndType() {
    WordModel source = importedWord("source");
    WordModel target = importedWord("target");
    createBatch(relationArray(
      relationJson(source.getId(), target.getId(), WordRelationType.RELATED),
      relationJson(target.getId(), source.getId(), WordRelationType.RELATED),
      relationJson(source.getId(), target.getId(), WordRelationType.SYNONYM)
    )).then().statusCode(201);

    deleteBatch(relationArray(relationJson(source.getId(), target.getId(), WordRelationType.RELATED)))
      .then().statusCode(204);

    given().when().get("/words/" + source.getId() + "/relations")
      .then().statusCode(200).body("", hasSize(1))
      .body("[0].relatedWordId", equalTo(target.getId().toString()))
      .body("[0].relation", equalTo("synonym"));
    given().when().get("/words/" + target.getId() + "/relations")
      .then().statusCode(200).body("", hasSize(1))
      .body("[0].relatedWordId", equalTo(source.getId().toString()))
      .body("[0].relation", equalTo("related"));
  }

  @Test
  @TestSecurity(user = "editor", roles = {"words#create", "words#delete"})
  void shouldRollbackDeleteBatchWhenAnyRelationDoesNotExist() {
    WordModel source = importedWord("source");
    WordModel firstTarget = importedWord("first-target");
    WordModel secondTarget = importedWord("second-target");
    createBatch(relationArray(
      relationJson(source.getId(), firstTarget.getId(), WordRelationType.RELATED),
      relationJson(source.getId(), secondTarget.getId(), WordRelationType.SYNONYM)
    )).then().statusCode(201);

    deleteBatch(relationArray(
      relationJson(source.getId(), firstTarget.getId(), WordRelationType.RELATED),
      relationJson(source.getId(), secondTarget.getId(), WordRelationType.RELATED)
    )).then().statusCode(404);

    given().when().get("/words/" + source.getId() + "/relations")
      .then().statusCode(200).body("", hasSize(2));
  }

  @Test
  @TestSecurity(user = "editor", roles = "words#delete")
  void shouldRejectInvalidDeleteBatchItems() {
    WordModel source = importedWord("source");
    WordModel target = importedWord("target");
    String relation = relationJson(source.getId(), target.getId(), WordRelationType.RELATED);

    deleteBatch("[]").then().statusCode(400);
    deleteBatch("[null]").then().statusCode(400);
    deleteBatch(relationArray(relation, relation)).then().statusCode(400);
    deleteBatch(relationArray(relationJson(source.getId(), null, WordRelationType.RELATED)))
      .then().statusCode(400);
  }

  @Test
  @TestSecurity(user = "importer", roles = "words#create")
  void shouldRejectInvalidCreateBatchItems() {
    WordModel source = importedWord("source");
    WordModel target = importedWord("target");
    WordModel manual = manualWord("manual");
    UUID missingId = UUID.randomUUID();
    String valid = relationJson(source.getId(), target.getId(), WordRelationType.RELATED);

    createBatch("[]").then().statusCode(400);
    createBatch(relationArray(valid, valid)).then().statusCode(400);
    createBatch("[null]").then().statusCode(400);
    createBatch(relationArray(relationJson(null, target.getId(), WordRelationType.RELATED)))
      .then().statusCode(400);
    createBatch(relationArray(relationJson(source.getId(), null, WordRelationType.RELATED)))
      .then().statusCode(400);
    createBatch(relationArray(relationJson(source.getId(), source.getId(), WordRelationType.RELATED)))
      .then().statusCode(400);
    createBatch(relationArray(relationJson(source.getId(), manual.getId(), WordRelationType.RELATED)))
      .then().statusCode(400);
    createBatch(relationArray(relationJson(manual.getId(), target.getId(), WordRelationType.RELATED)))
      .then().statusCode(400);
    createBatch(relationArray(relationJson(source.getId(), target.getId(), (String) null)))
      .then().statusCode(400);
    createBatch(relationArray(relationJson(source.getId(), target.getId(), "unsupported")))
      .then().statusCode(400);
    createBatch(relationArray(relationJson(missingId, target.getId(), WordRelationType.RELATED)))
      .then().statusCode(404);
    createBatch(relationArray(relationJson(source.getId(), missingId, WordRelationType.RELATED)))
      .then().statusCode(404);
  }

  @Test
  @TestSecurity(user = "importer", roles = "words#create")
  void shouldRejectBatchLargerThanConfiguredLimit() {
    String body = IntStream.range(0, 1001)
      .mapToObj(index -> relationJson(UUID.randomUUID(), UUID.randomUUID(), WordRelationType.RELATED))
      .collect(Collectors.joining(",", "[", "]"));

    createBatch(body).then().statusCode(400);
  }

  @Test
  void shouldRequireAuthenticationForBatchMutations() {
    String body = relationArray(relationJson(UUID.randomUUID(), UUID.randomUUID(), WordRelationType.RELATED));

    createBatch(body).then().statusCode(401);
    deleteBatch(body).then().statusCode(401);
  }

  @Test
  @TestSecurity(user = "reader")
  void shouldRequireCreateAndDeleteRoles() {
    String body = relationArray(relationJson(UUID.randomUUID(), UUID.randomUUID(), WordRelationType.RELATED));

    createBatch(body).then().statusCode(403);
    deleteBatch(body).then().statusCode(403);
  }

  private Response createBatch(String body) {
    return given().contentType("application/json").body(body)
      .when().post("/words/relations/batch");
  }

  private Response deleteBatch(String body) {
    return given().contentType("application/json").body(body)
      .when().delete("/words/relations/batch");
  }

  private String relationJson(UUID wordId, UUID relatedWordId, WordRelationType relation) {
    return relationJson(wordId, relatedWordId, relation == null ? null : relation.value());
  }

  private String relationJson(UUID wordId, UUID relatedWordId, String relation) {
    return "{\"wordId\":" + uuidJson(wordId) + ",\"relatedWordId\":" + uuidJson(relatedWordId)
      + ",\"relation\":" + (relation == null ? "null" : "\"" + relation + "\"") + "}";
  }

  private String uuidJson(UUID id) {
    return id == null ? "null" : "\"" + id + "\"";
  }

  private String relationArray(String... relations) {
    return "[" + String.join(",", relations) + "]";
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

  private WordModel manualWord(String suffix) {
    WordModel word = WordModel.builder()
      .id(UUID.randomUUID())
      .russian(suffix)
      .checksum("initial-checksum")
      .type(WordType.OTHER)
      .translations(new ArrayList<>())
      .build();
    return persist(word);
  }
}
