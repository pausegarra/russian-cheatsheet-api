package es.pausegarra.russian_cheatsheet.integration.words;

import es.pausegarra.russian_cheatsheet.base.IntegrationTest;
import es.pausegarra.russian_cheatsheet.context.words.application.dto.WordTranslationInputDto;
import es.pausegarra.russian_cheatsheet.context.words.application.WordRelationChecksumService;
import es.pausegarra.russian_cheatsheet.context.words.domain.entities.WordEntity;
import es.pausegarra.russian_cheatsheet.context.words.domain.entities.WordFormsEntity;
import es.pausegarra.russian_cheatsheet.context.words.domain.enums.WordAspect;
import es.pausegarra.russian_cheatsheet.context.words.domain.enums.WordRelationType;
import es.pausegarra.russian_cheatsheet.context.words.domain.enums.WordType;
import es.pausegarra.russian_cheatsheet.context.words.infrastructure.models.WordModel;
import es.pausegarra.russian_cheatsheet.context.words.infrastructure.models.WordRelationModel;
import es.pausegarra.russian_cheatsheet.context.words.infrastructure.requests.UpdateWordRequest;
import es.pausegarra.russian_cheatsheet.mother.WordMother;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
public class UpdateWordsIT extends IntegrationTest {

  @Test
  @TestSecurity(user = "user", roles = "words#update")
  public void shouldUpdateWord() throws Exception {
    WordEntity word = WordMother.random().id(null).build();
    WordModel saved = persist(WordModel.fromEntity(word));
    UpdateWordRequest request = new UpdateWordRequest(
      "newRussian", WordType.OTHER, null, null, null,
      List.of(new WordTranslationInputDto("es", "nueva traducción", 0)), null, null
    );

    given().body(objectMapper.writeValueAsString(request)).contentType("application/json")
      .when().put("/api/v1/words/" + saved.getId())
      .then().statusCode(200).body("checksum", equalTo(null));

    WordModel updated = em.find(WordModel.class, saved.getId());
    assertNotNull(updated);
    assertEquals("newRussian", updated.getRussian());
    assertEquals(WordType.OTHER, updated.getType());
    assertNull(updated.getForms());
    assertNull(updated.getAspect());
  }

  @Test
  @TestSecurity(user = "user", roles = "words#update")
  public void shouldRefreshIncomingRelationChecksumWhenTargetRussianChanges() throws Exception {
    WordModel source = persist(WordModel.fromEntity(WordEntity.createImported(
      "source-word", "источник", List.of(), null, null, WordType.NOUN, null, null
    )));
    WordModel target = persist(WordModel.fromEntity(WordEntity.createImported(
      "target-word", "цель", List.of(), null, null, WordType.NOUN, null, null
    )));
    WordRelationModel relation = new WordRelationModel();
    relation.setSourceWord(source);
    relation.setTargetWord(target);
    relation.setRelation(WordRelationType.RELATED);
    relation.setChecksum(new WordRelationChecksumService().calculate(
      "источник", "цель", WordRelationType.RELATED
    ));
    persist(relation);

    UpdateWordRequest request = new UpdateWordRequest(
      "новая цель", WordType.NOUN, null, null, target.getExternalId(), List.of(), null, null
    );
    given().body(objectMapper.writeValueAsString(request)).contentType("application/json")
      .when().put("/api/v1/words/" + target.getId())
      .then().statusCode(200);

    String storedChecksum = (String) em.createNativeQuery(
        "select checksum from word_relations where id = :relationId"
      )
      .setParameter("relationId", relation.getId())
      .getSingleResult();
    assertEquals(
      new WordRelationChecksumService().calculate("источник", "новая цель", WordRelationType.RELATED),
      storedChecksum
    );
  }

  @Test
  @TestSecurity(user = "user", roles = "words#update")
  public void shouldUpdateVerbFormsAndAspect() throws Exception {
    WordEntity word = WordMother.random().id(null).type(WordType.OTHER).build();
    WordModel saved = persist(WordModel.fromEntity(word));
    WordFormsEntity forms = WordFormsEntity.builder()
      .ru_verb_presfut_sg1("иду")
      .ru_verb_gerund_present("идя")
      .build();
    UpdateWordRequest request = new UpdateWordRequest(
      "идти", WordType.VERB, WordAspect.IMPERFECTIVE, forms, null,
      List.of(new WordTranslationInputDto("es", "ir", 0)), "Usage", "https://example.org/audio.mp3"
    );

    given().body(objectMapper.writeValueAsString(request)).contentType("application/json")
      .when().put("/api/v1/words/" + saved.getId())
      .then().statusCode(200)
        .body("aspect", equalTo("imperfective"))
        .body("forms.ru_verb_presfut_sg1", equalTo("иду"))
        .body("forms.ru_verb_gerund_present", equalTo("идя"));

    WordModel updated = em.find(WordModel.class, saved.getId());
    assertEquals(WordAspect.IMPERFECTIVE, updated.getAspect());
    assertEquals(forms, updated.getForms());
  }

  @Test
  @TestSecurity(user = "user", roles = "words#update")
  public void shouldRejectFormsThatDoNotMatchTheUpdatedType() throws Exception {
    WordEntity word = WordMother.random().id(null).type(WordType.OTHER).build();
    WordModel saved = persist(WordModel.fromEntity(word));
    UpdateWordRequest request = new UpdateWordRequest(
      "идти", WordType.VERB, WordAspect.PERFECTIVE,
      WordFormsEntity.builder().ru_noun_sg_nom("дом").build(), null, List.of(), null, null
    );

    given().body(objectMapper.writeValueAsString(request)).contentType("application/json")
      .when().put("/api/v1/words/" + saved.getId())
      .then().statusCode(400).body("code", equalTo("BAD_REQUEST"));
  }

  @Test
  @TestSecurity(user = "user", roles = "words#update")
  public void shouldReturn404IfWordNotFound() throws Exception {
    UpdateWordRequest request = new UpdateWordRequest(
      "newRussian", WordType.OTHER, null, null, null, List.of(), null, null
    );
    given().body(objectMapper.writeValueAsString(request)).contentType("application/json")
      .when().put("/api/v1/words/" + UUID.randomUUID()).then().statusCode(404);
  }

  @Test
  public void shouldReturn401IfUserIsNotAuthenticated() throws Exception {
    UpdateWordRequest request = new UpdateWordRequest(
      "newRussian", WordType.OTHER, null, null, null, List.of(), null, null
    );
    given().body(objectMapper.writeValueAsString(request)).contentType("application/json")
      .when().put("/api/v1/words/" + UUID.randomUUID()).then().statusCode(401);
  }

  @Test
  @TestSecurity(user = "user")
  public void shouldReturn403IfUserIsNotAuthorized() throws Exception {
    UpdateWordRequest request = new UpdateWordRequest(
      "newRussian", WordType.OTHER, null, null, null, List.of(), null, null
    );
    given().body(objectMapper.writeValueAsString(request)).contentType("application/json")
      .when().put("/api/v1/words/" + UUID.randomUUID()).then().statusCode(403);
  }
}
