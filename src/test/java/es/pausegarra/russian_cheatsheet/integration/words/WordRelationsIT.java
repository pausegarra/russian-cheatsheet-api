package es.pausegarra.russian_cheatsheet.integration.words;

import es.pausegarra.russian_cheatsheet.base.IntegrationTest;
import es.pausegarra.russian_cheatsheet.context.words.application.WordRelationChecksumService;
import es.pausegarra.russian_cheatsheet.context.words.application.WordRelationsService;
import es.pausegarra.russian_cheatsheet.context.words.application.dto.WordRelationInputDto;
import es.pausegarra.russian_cheatsheet.context.words.domain.enums.WordRelationType;
import es.pausegarra.russian_cheatsheet.context.words.domain.enums.WordType;
import es.pausegarra.russian_cheatsheet.context.words.application.use_cases.create_word_relation.CreateWordRelationResultDto;
import es.pausegarra.russian_cheatsheet.context.words.infrastructure.models.WordModel;
import es.pausegarra.russian_cheatsheet.context.words.infrastructure.models.WordRelationModel;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import io.restassured.response.Response;
import jakarta.inject.Inject;
import jakarta.transaction.UserTransaction;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@QuarkusTest
class WordRelationsIT extends IntegrationTest {

  @Inject
  WordRelationsService relationsService;

  @Inject
  UserTransaction userTransaction;

  @Inject
  Flyway flyway;

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
      .body("data[0].sourceExternalId", equalTo(firstSource.getExternalId()))
      .body("data[0].relatedWordId", equalTo(firstTarget.getId().toString()))
      .body("data[0].relatedExternalId", equalTo(firstTarget.getExternalId()))
      .body("data[0].relatedWord.id", equalTo(firstTarget.getId().toString()))
      .body("data[0].relatedWord.externalId", equalTo(firstTarget.getExternalId()))
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
      .body("data[0].sourceExternalId", equalTo(secondSource.getExternalId()))
      .body("data[0].relatedWord.russian", equalTo("цель-б"));
  }

  @Test
  void shouldNotPersistRussianSnapshotsOnWordRelations() {
    long snapshotColumnCount = ((Number) em.createNativeQuery(
        "select count(*) from information_schema.columns " +
          "where table_schema = current_schema() and table_name = 'word_relations' " +
          "and column_name in ('source_russian', 'related_russian')"
      )
      .getSingleResult()).longValue();

    assertEquals(0L, snapshotColumnCount);
  }

  @Test
  @TestSecurity(user = "importer", roles = "words#create")
  void shouldAllowRelationsBetweenDifferentExternalWordsWithSameRussian() {
    WordModel source = importedWord("омоним");
    WordModel target = importedWord("омоним");
    assertNotEquals(source.getExternalId(), target.getExternalId());

    createRelation(source.getId(), target.getId())
      .then().statusCode(201)
      .body("relatedWordId", equalTo(target.getId().toString()))
      .body("checksum", equalTo(
        new WordRelationChecksumService().calculate("омоним", "омоним", WordRelationType.RELATED)
      ));
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

    given().when().get("/api/v1/words/relations?page=0&perPage=1001")
      .then().statusCode(400);
  }

  @Test
  void shouldSupportLegacyRelationWritesAndRussianUpdates() throws Exception {
    flyway.clean();
    flyway.migrate();
    WordModel source = importedWord("legacy-source");
    WordModel target = importedWord("legacy-target");
    UUID relationId = UUID.randomUUID();

    userTransaction.begin();
    try {
      int inserted = assertDoesNotThrow(() -> em.createNativeQuery(
          "insert into word_relations (id, source_word_id, target_word_id, relation) " +
            "values (:id, :sourceId, :targetId, 'related')"
        )
        .setParameter("id", relationId)
        .setParameter("sourceId", source.getId())
        .setParameter("targetId", target.getId())
        .executeUpdate());
      assertEquals(1, inserted);
      assertEquals(
        new WordRelationChecksumService().calculate("legacy-source", "legacy-target", WordRelationType.RELATED),
        storedRelationChecksum(relationId)
      );

      em.createNativeQuery("update words set russian = :russian where id = :targetId")
        .setParameter("russian", "legacy-target-updated")
        .setParameter("targetId", target.getId())
        .executeUpdate();

      assertEquals(
        new WordRelationChecksumService().calculate(
          "legacy-source", "legacy-target-updated", WordRelationType.RELATED
        ),
        storedRelationChecksum(relationId)
      );
      userTransaction.commit();
    } catch (Exception | Error failure) {
      userTransaction.rollback();
      throw failure;
    }
  }

  @Test
  void shouldWaitForConcurrentRussianUpdateBeforeCreatingRelation() throws Exception {
    WordModel source = importedWord("locked-source");
    WordModel target = importedWord("locked-target");
    CountDownLatch updateApplied = new CountDownLatch(1);
    CountDownLatch allowUpdateCommit = new CountDownLatch(1);
    ExecutorService executor = Executors.newFixedThreadPool(2);
    Future<?> updateFuture = executor.submit(() -> {
      try {
        userTransaction.begin();
        int updated = em.createNativeQuery("update words set russian = :russian where id = :targetId")
          .setParameter("russian", "locked-target-updated")
          .setParameter("targetId", target.getId())
          .executeUpdate();
        assertEquals(1, updated);
        updateApplied.countDown();
        if (!allowUpdateCommit.await(5, TimeUnit.SECONDS)) {
          throw new IllegalStateException("Timed out waiting to commit word update");
        }
        userTransaction.commit();
      } catch (Exception exception) {
        try {
          userTransaction.rollback();
        } catch (Exception rollbackException) {
          exception.addSuppressed(rollbackException);
        }
        throw new RuntimeException(exception);
      }
    });

    try {
      assertTrue(updateApplied.await(5, TimeUnit.SECONDS));
      Future<CreateWordRelationResultDto> relationFuture = executor.submit(() -> relationsService.createOutgoing(
        source.getId(), new WordRelationInputDto(target.getId(), WordRelationType.RELATED)
      ));
      assertThrows(TimeoutException.class, () -> relationFuture.get(1, TimeUnit.SECONDS));
      allowUpdateCommit.countDown();
      updateFuture.get(5, TimeUnit.SECONDS);
      CreateWordRelationResultDto created = relationFuture.get(5, TimeUnit.SECONDS);
      assertEquals(
        new WordRelationChecksumService().calculate(
          "locked-source", "locked-target-updated", WordRelationType.RELATED
        ),
        storedRelationChecksum(created.relation().id())
      );
    } finally {
      allowUpdateCommit.countDown();
      executor.shutdownNow();
    }
  }

  @Test
  void shouldKeepChecksumCurrentWhenBothRelatedWordsChangeConcurrently() throws Exception {
    flyway.clean();
    flyway.migrate();
    WordModel source = importedWord("concurrent-source");
    WordModel target = importedWord("concurrent-target");
    UUID relationId = persistRelation(source, target);
    CountDownLatch sourceUpdated = new CountDownLatch(1);
    CountDownLatch allowSourceCommit = new CountDownLatch(1);
    ExecutorService executor = Executors.newFixedThreadPool(2);
    Future<?> sourceUpdate = executor.submit(() -> updateRussianAndHold(
      source.getId(), "concurrent-source-updated", sourceUpdated, allowSourceCommit
    ));

    try {
      assertTrue(sourceUpdated.await(5, TimeUnit.SECONDS));
      Future<?> targetUpdate = executor.submit(() -> updateRussian(target.getId(), "concurrent-target-updated"));
      assertThrows(TimeoutException.class, () -> targetUpdate.get(1, TimeUnit.SECONDS));
      allowSourceCommit.countDown();
      sourceUpdate.get(5, TimeUnit.SECONDS);
      targetUpdate.get(5, TimeUnit.SECONDS);
    } finally {
      allowSourceCommit.countDown();
      executor.shutdownNow();
    }

    assertEquals(
      new WordRelationChecksumService().calculate(
        "concurrent-source-updated", "concurrent-target-updated", WordRelationType.RELATED
      ),
      storedRelationChecksum(relationId)
    );
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

  private String storedRelationChecksum(UUID relationId) {
    return (String) em.createNativeQuery("select checksum from word_relations where id = :relationId")
      .setParameter("relationId", relationId)
      .getSingleResult();
  }

  private UUID persistRelation(WordModel source, WordModel target) {
    WordRelationModel relation = new WordRelationModel();
    relation.setSourceWord(source);
    relation.setTargetWord(target);
    relation.setRelation(WordRelationType.RELATED);
    relation.setChecksum(new WordRelationChecksumService().calculate(
      source.getRussian(), target.getRussian(), WordRelationType.RELATED
    ));
    persist(relation);
    return relation.getId();
  }

  private void updateRussianAndHold(
    UUID wordId,
    String russian,
    CountDownLatch updated,
    CountDownLatch allowCommit
  ) {
    try {
      userTransaction.begin();
      int updatedRows = em.createNativeQuery("update words set russian = :russian where id = :wordId")
        .setParameter("russian", russian)
        .setParameter("wordId", wordId)
        .executeUpdate();
      assertEquals(1, updatedRows);
      updated.countDown();
      if (!allowCommit.await(5, TimeUnit.SECONDS)) {
        throw new IllegalStateException("Timed out waiting to commit Russian update");
      }
      userTransaction.commit();
    } catch (Exception | Error failure) {
      try {
        userTransaction.rollback();
      } catch (Exception rollbackException) {
        failure.addSuppressed(rollbackException);
      }
      throw new RuntimeException(failure);
    }
  }

  private void updateRussian(UUID wordId, String russian) {
    try {
      userTransaction.begin();
      int updatedRows = em.createNativeQuery("update words set russian = :russian where id = :wordId")
        .setParameter("russian", russian)
        .setParameter("wordId", wordId)
        .executeUpdate();
      assertEquals(1, updatedRows);
      userTransaction.commit();
    } catch (Exception | Error failure) {
      try {
        userTransaction.rollback();
      } catch (Exception rollbackException) {
        failure.addSuppressed(rollbackException);
      }
      throw new RuntimeException(failure);
    }
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
