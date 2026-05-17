package es.pausegarra.russian_cheatsheet.integration.words;

import es.pausegarra.russian_cheatsheet.base.IntegrationTest;
import es.pausegarra.russian_cheatsheet.context.words.application.use_cases.create_word.CreateWordDeclinationDto;
import es.pausegarra.russian_cheatsheet.context.words.application.use_cases.update_word.UpdateWordConjugationsDto;
import es.pausegarra.russian_cheatsheet.context.words.application.use_cases.update_word.UpdateWordDeclinationDto;
import es.pausegarra.russian_cheatsheet.context.words.application.use_cases.update_word.UpdateWordDeclinationMatrixDto;
import es.pausegarra.russian_cheatsheet.context.words.domain.entities.WordEntity;
import es.pausegarra.russian_cheatsheet.context.words.domain.enums.WordType;
import es.pausegarra.russian_cheatsheet.context.words.infrastructure.models.WordModel;
import es.pausegarra.russian_cheatsheet.context.words.infrastructure.requests.UpdateWordRequest;
import es.pausegarra.russian_cheatsheet.mother.WordMother;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
public class UpdateWordsIT extends IntegrationTest {

  @Test
  @TestSecurity(user = "user", roles = "words#update")
  public void shouldUpdateWord() throws Exception {
    WordEntity word = WordMother.random().id(null).build();
    WordModel saved = persist(WordModel.fromEntity(word));

    UpdateWordRequest dto = new UpdateWordRequest("newRussian", "newSpanish", "newEnglish", "OTHER", null, null, null);
    String json = objectMapper.writeValueAsString(dto);

    given().body(json).contentType("application/json").when().put("/words/" + saved.getId().toString()).then().statusCode(200);

    WordModel updated = em.find(WordModel.class, saved.getId());
    assertNotNull(updated);

    assertEquals(dto.russian(), updated.getRussian());
    assertEquals(dto.english(), updated.getEnglish());
    assertEquals(dto.spanish(), updated.getSpanish());
    assertEquals(dto.type(), updated.getType().toString());
    assertNull(updated.getConjugations());
    assertNull(updated.getDeclinations());
    assertNull(updated.getDeclinationMatrix());
  }

  @Test
  @TestSecurity(user = "user", roles = "words#update")
  public void shouldUpdateWordWithConjugations() throws Exception {
    WordEntity word = WordMother.random().id(null).type(WordType.OTHER).build();
    WordModel saved = persist(WordModel.fromEntity(word));

    UpdateWordRequest dto = new UpdateWordRequest(
      "newRussian", "newSpanish", "newEnglish", "VERB", null, null, new UpdateWordConjugationsDto(
      "imperfectivePresentFirstPersonSingular",
      "imperfectivePresentSecondPersonSingular",
      "imperfectivePresentThirdPersonSingular",
      "imperfectivePresentFirstPersonPlural",
      "imperfectivePresentSecondPersonPlural",
      "imperfectivePresentThirdPersonPlural",
      "imperfectivePastMasculine",
      "imperfectivePastFeminine",
      "imperfectivePastNeuter",
      "imperfectivePastPlural",
      "imperfectiveFutureFirstPersonSingular",
      "imperfectiveFutureSecondPersonSingular",
      "imperfectiveFutureThirdPersonSingular",
      "imperfectiveFutureFirstPersonPlural",
      "imperfectiveFutureSecondPersonPlural",
      "imperfectiveFutureThirdPersonPlural",
      "perfectivePastMasculine",
      "perfectivePastFeminine",
      "perfectivePastNeuter",
      "perfectivePastPlural",
      "perfectiveFutureFirstPersonSingular",
      "perfectiveFutureSecondPersonSingular",
      "perfectiveFutureThirdPersonSingular",
      "perfectiveFutureFirstPersonPlural",
      "perfectiveFutureSecondPersonPlural",
      "perfectiveFutureThirdPersonPlural",
      "imperfectiveImperativeSecondPersonSingular",
      "imperfectiveImperativeSecondPersonPlural",
      "perfectiveImperativeSecondPersonSingular",
      "perfectiveImperativeSecondPersonPlural"
    )
    );
    String json = objectMapper.writeValueAsString(dto);

    given().body(json).contentType("application/json").when().put("/words/" + saved.getId().toString()).then().statusCode(200);

    WordModel updated = em.find(WordModel.class, saved.getId());
    assertNotNull(updated);

    assertEquals(dto.russian(), updated.getRussian());
    assertEquals(dto.english(), updated.getEnglish());
    assertEquals(dto.spanish(), updated.getSpanish());
    assertEquals(dto.type(), updated.getType().toString());

    assertNotNull(updated.getConjugations());
    assertEquals(dto.conjugations().imperfectivePresentFirstPersonSingular(), updated.getConjugations().imperfectivePresentFirstPersonSingular());
    assertEquals(dto.conjugations().imperfectivePresentSecondPersonSingular(), updated.getConjugations().imperfectivePresentSecondPersonSingular());
    assertEquals(dto.conjugations().imperfectivePresentThirdPersonSingular(), updated.getConjugations().imperfectivePresentThirdPersonSingular());
    assertEquals(dto.conjugations().imperfectivePresentFirstPersonPlural(), updated.getConjugations().imperfectivePresentFirstPersonPlural());
    assertEquals(dto.conjugations().imperfectivePresentSecondPersonPlural(), updated.getConjugations().imperfectivePresentSecondPersonPlural());
    assertEquals(dto.conjugations().imperfectivePresentThirdPersonPlural(), updated.getConjugations().imperfectivePresentThirdPersonPlural());
    assertEquals(dto.conjugations().imperfectivePastMasculine(), updated.getConjugations().imperfectivePastMasculine());
    assertEquals(dto.conjugations().imperfectivePastFeminine(), updated.getConjugations().imperfectivePastFeminine());
    assertEquals(dto.conjugations().imperfectivePastNeuter(), updated.getConjugations().imperfectivePastNeuter());
    assertEquals(dto.conjugations().imperfectivePastPlural(), updated.getConjugations().imperfectivePastPlural());
    assertEquals(dto.conjugations().imperfectiveFutureFirstPersonSingular(), updated.getConjugations().imperfectiveFutureFirstPersonSingular());
    assertEquals(dto.conjugations().imperfectiveFutureSecondPersonSingular(), updated.getConjugations().imperfectiveFutureSecondPersonSingular());
    assertEquals(dto.conjugations().imperfectiveFutureThirdPersonSingular(), updated.getConjugations().imperfectiveFutureThirdPersonSingular());
    assertEquals(dto.conjugations().imperfectiveFutureFirstPersonPlural(), updated.getConjugations().imperfectiveFutureFirstPersonPlural());
    assertEquals(dto.conjugations().imperfectiveFutureSecondPersonPlural(), updated.getConjugations().imperfectiveFutureSecondPersonPlural());
    assertEquals(dto.conjugations().imperfectiveFutureThirdPersonPlural(), updated.getConjugations().imperfectiveFutureThirdPersonPlural());
    assertEquals(dto.conjugations().perfectivePastMasculine(), updated.getConjugations().perfectivePastMasculine());
    assertEquals(dto.conjugations().perfectivePastFeminine(), updated.getConjugations().perfectivePastFeminine());
    assertEquals(dto.conjugations().perfectivePastNeuter(), updated.getConjugations().perfectivePastNeuter());
    assertEquals(dto.conjugations().perfectivePastPlural(), updated.getConjugations().perfectivePastPlural());
    assertEquals(dto.conjugations().perfectiveFutureFirstPersonSingular(), updated.getConjugations().perfectiveFutureFirstPersonSingular());
    assertEquals(dto.conjugations().perfectiveFutureSecondPersonSingular(), updated.getConjugations().perfectiveFutureSecondPersonSingular());
    assertEquals(dto.conjugations().perfectiveFutureThirdPersonSingular(), updated.getConjugations().perfectiveFutureThirdPersonSingular());
    assertEquals(dto.conjugations().perfectiveFutureFirstPersonPlural(), updated.getConjugations().perfectiveFutureFirstPersonPlural());
    assertEquals(dto.conjugations().perfectiveFutureSecondPersonPlural(), updated.getConjugations().perfectiveFutureSecondPersonPlural());
    assertEquals(dto.conjugations().perfectiveFutureThirdPersonPlural(), updated.getConjugations().perfectiveFutureThirdPersonPlural());
    assertEquals(
      dto.conjugations().imperfectiveImperativeSecondPersonSingular(),
      updated.getConjugations().imperfectiveImperativeSecondPersonSingular()
    );
    assertEquals(
      dto.conjugations().imperfectiveImperativeSecondPersonPlural(),
      updated.getConjugations().imperfectiveImperativeSecondPersonPlural()
    );
    assertEquals(
      dto.conjugations().perfectiveImperativeSecondPersonSingular(),
      updated.getConjugations().perfectiveImperativeSecondPersonSingular()
    );
    assertEquals(
      dto.conjugations().perfectiveImperativeSecondPersonPlural(),
      updated.getConjugations().perfectiveImperativeSecondPersonPlural()
    );
  }

  @Test
  @TestSecurity(user = "user", roles = "words#update")
  public void shouldUpdateWordWithDeclinations() throws Exception {
    WordEntity word = WordMother.random().id(null).type(WordType.VERB).build();
    WordModel saved = persist(WordModel.fromEntity(word));

    UpdateWordRequest dto = new UpdateWordRequest(
      "newRussian",
      "newSpanish",
      "newEnglish",
      "NOUN",
      new UpdateWordDeclinationDto(
        "nominative",
        "accusative",
        "genitive",
        "dative",
        "instrumental",
        "prepositional",
        "nominativePlural",
        "accusativePlural",
        "genitivePlural",
        "dativePlural",
        "instrumentalPlural",
        "prepositionalPlural"
      ),
      null,
      null
    );
    String json = objectMapper.writeValueAsString(dto);

    given().body(json).contentType("application/json").when().put("/words/" + saved.getId().toString()).then().statusCode(200);

    WordModel updated = em.find(WordModel.class, saved.getId());
    assertNotNull(updated);

    assertEquals(dto.russian(), updated.getRussian());
    assertEquals(dto.english(), updated.getEnglish());
    assertEquals(dto.spanish(), updated.getSpanish());
    assertEquals(dto.type(), updated.getType().toString());

    assertNotNull(updated.getDeclinations());
    assertEquals(dto.declinations().nominative(), updated.getDeclinations().nominative());
    assertEquals(dto.declinations().accusative(), updated.getDeclinations().accusative());
    assertEquals(dto.declinations().genitive(), updated.getDeclinations().genitive());
    assertEquals(dto.declinations().dative(), updated.getDeclinations().dative());
    assertEquals(dto.declinations().instrumental(), updated.getDeclinations().instrumental());
    assertEquals(dto.declinations().prepositional(), updated.getDeclinations().prepositional());
  }

  @Test
  @TestSecurity(user = "user", roles = "words#update")
  public void shouldUpdateWordWithDeclinationsWhenTypeIsPronounNoun() throws Exception {
    WordEntity word = WordMother.random().id(null).type(WordType.VERB).build();
    WordModel saved = persist(WordModel.fromEntity(word));

    UpdateWordRequest dto = new UpdateWordRequest(
      "newRussian",
      "newSpanish",
      "newEnglish",
      "PRONOUN_NOUN",
      new UpdateWordDeclinationDto(
        "nominative",
        "accusative",
        "genitive",
        "dative",
        "instrumental",
        "prepositional",
        "nominativePlural",
        "accusativePlural",
        "genitivePlural",
        "dativePlural",
        "instrumentalPlural",
        "prepositionalPlural"
      ),
      null,
      null
    );
    String json = objectMapper.writeValueAsString(dto);

    given().body(json).contentType("application/json").when().put("/words/" + saved.getId().toString()).then().statusCode(200);

    WordModel updated = em.find(WordModel.class, saved.getId());
    assertNotNull(updated);
    assertEquals("PRONOUN_NOUN", updated.getType().toString());
    assertNotNull(updated.getDeclinations());
  }

  @Test
  @TestSecurity(user = "user", roles = "words#update")
  public void shouldUpdateWordWithDeclinationsWhenTypeIsNumeralCardinal() throws Exception {
    WordEntity word = WordMother.random().id(null).type(WordType.VERB).build();
    WordModel saved = persist(WordModel.fromEntity(word));

    UpdateWordRequest dto = new UpdateWordRequest(
      "newRussian",
      "newSpanish",
      "newEnglish",
      "NUMERAL_CARDINAL",
      new UpdateWordDeclinationDto(
        "nominative",
        "accusative",
        "genitive",
        "dative",
        "instrumental",
        "prepositional",
        "nominativePlural",
        "accusativePlural",
        "genitivePlural",
        "dativePlural",
        "instrumentalPlural",
        "prepositionalPlural"
      ),
      null,
      null
    );
    String json = objectMapper.writeValueAsString(dto);

    given().body(json).contentType("application/json").when().put("/words/" + saved.getId().toString()).then().statusCode(200);

    WordModel updated = em.find(WordModel.class, saved.getId());
    assertNotNull(updated);
    assertEquals("NUMERAL_CARDINAL", updated.getType().toString());
    assertNotNull(updated.getDeclinations());
  }

  @Test
  @TestSecurity(user = "user", roles = "words#update")
  public void shouldUpdateWordWithDeclinationMatrix() throws Exception {
    WordEntity word = WordMother.random().id(null).type(WordType.NOUN).build();
    WordModel saved = persist(WordModel.fromEntity(word));

    UpdateWordRequest dto = new UpdateWordRequest(
      "newRussian", "newSpanish", "newEnglish", "ADJECTIVE", null, new UpdateWordDeclinationMatrixDto(
      "nominativeMasculine",
      "nominativeFeminine",
      "nominativeNeuter",
      "nominativePlural",
      "accusativeMasculine",
      "accusativeFeminine",
      "accusativeNeuter",
      "accusativePlural",
      "genitiveMasculine",
      "genitiveFeminine",
      "genitiveNeuter",
      "genitivePlural",
      "dativeMasculine",
      "dativeFeminine",
      "dativeNeuter",
      "dativePlural",
      "instrumentalMasculine",
      "instrumentalFeminine",
      "instrumentalNeuter",
      "instrumentalPlural",
      "prepositionalMasculine",
      "prepositionalFeminine",
      "prepositionalNeuter",
      "prepositionalPlural"
    ), null
    );
    String json = objectMapper.writeValueAsString(dto);

    given().body(json).contentType("application/json").when().put("/words/" + saved.getId().toString()).then().statusCode(200);

    WordModel updated = em.find(WordModel.class, saved.getId());
    assertNotNull(updated);

    assertEquals(dto.russian(), updated.getRussian());
    assertEquals(dto.english(), updated.getEnglish());
    assertEquals(dto.spanish(), updated.getSpanish());
    assertEquals(dto.type(), updated.getType().toString());

    assertNotNull(updated.getDeclinationMatrix());
    assertEquals(dto.declinationMatrix().nominativeMasculine(), updated.getDeclinationMatrix().nominativeMasculine());
    assertEquals(dto.declinationMatrix().nominativeFeminine(), updated.getDeclinationMatrix().nominativeFeminine());
    assertEquals(dto.declinationMatrix().nominativeNeuter(), updated.getDeclinationMatrix().nominativeNeuter());
    assertEquals(dto.declinationMatrix().nominativePlural(), updated.getDeclinationMatrix().nominativePlural());
    assertEquals(dto.declinationMatrix().accusativeMasculine(), updated.getDeclinationMatrix().accusativeMasculine());
    assertEquals(dto.declinationMatrix().accusativeFeminine(), updated.getDeclinationMatrix().accusativeFeminine());
    assertEquals(dto.declinationMatrix().accusativeNeuter(), updated.getDeclinationMatrix().accusativeNeuter());
    assertEquals(dto.declinationMatrix().accusativePlural(), updated.getDeclinationMatrix().accusativePlural());
    assertEquals(dto.declinationMatrix().genitiveMasculine(), updated.getDeclinationMatrix().genitiveMasculine());
    assertEquals(dto.declinationMatrix().genitiveFeminine(), updated.getDeclinationMatrix().genitiveFeminine());
    assertEquals(dto.declinationMatrix().genitiveNeuter(), updated.getDeclinationMatrix().genitiveNeuter());
    assertEquals(dto.declinationMatrix().genitivePlural(), updated.getDeclinationMatrix().genitivePlural());
    assertEquals(dto.declinationMatrix().dativeMasculine(), updated.getDeclinationMatrix().dativeMasculine());
    assertEquals(dto.declinationMatrix().dativeFeminine(), updated.getDeclinationMatrix().dativeFeminine());
    assertEquals(dto.declinationMatrix().dativeNeuter(), updated.getDeclinationMatrix().dativeNeuter());
    assertEquals(dto.declinationMatrix().dativePlural(), updated.getDeclinationMatrix().dativePlural());
    assertEquals(dto.declinationMatrix().instrumentalMasculine(), updated.getDeclinationMatrix().instrumentalMasculine());
    assertEquals(dto.declinationMatrix().instrumentalFeminine(), updated.getDeclinationMatrix().instrumentalFeminine());
    assertEquals(dto.declinationMatrix().instrumentalNeuter(), updated.getDeclinationMatrix().instrumentalNeuter());
    assertEquals(dto.declinationMatrix().instrumentalPlural(), updated.getDeclinationMatrix().instrumentalPlural());
    assertEquals(dto.declinationMatrix().prepositionalMasculine(), updated.getDeclinationMatrix().prepositionalMasculine());
    assertEquals(dto.declinationMatrix().prepositionalFeminine(), updated.getDeclinationMatrix().prepositionalFeminine());
    assertEquals(dto.declinationMatrix().prepositionalNeuter(), updated.getDeclinationMatrix().prepositionalNeuter());
    assertEquals(dto.declinationMatrix().prepositionalPlural(), updated.getDeclinationMatrix().prepositionalPlural());
  }

  @Test
  @TestSecurity(user = "user", roles = "words#update")
  public void shouldUpdateWordWithDeclinationMatrixWhenTypeIsPronounAdjective() throws Exception {
    WordEntity word = WordMother.random().id(null).type(WordType.NOUN).build();
    WordModel saved = persist(WordModel.fromEntity(word));

    UpdateWordRequest dto = new UpdateWordRequest(
      "newRussian", "newSpanish", "newEnglish", "PRONOUN_ADJECTIVE", null, new UpdateWordDeclinationMatrixDto(
      "nominativeMasculine",
      "nominativeFeminine",
      "nominativeNeuter",
      "nominativePlural",
      "accusativeMasculine",
      "accusativeFeminine",
      "accusativeNeuter",
      "accusativePlural",
      "genitiveMasculine",
      "genitiveFeminine",
      "genitiveNeuter",
      "genitivePlural",
      "dativeMasculine",
      "dativeFeminine",
      "dativeNeuter",
      "dativePlural",
      "instrumentalMasculine",
      "instrumentalFeminine",
      "instrumentalNeuter",
      "instrumentalPlural",
      "prepositionalMasculine",
      "prepositionalFeminine",
      "prepositionalNeuter",
      "prepositionalPlural"
    ), null
    );
    String json = objectMapper.writeValueAsString(dto);

    given().body(json).contentType("application/json").when().put("/words/" + saved.getId().toString()).then().statusCode(200);

    WordModel updated = em.find(WordModel.class, saved.getId());
    assertNotNull(updated);
    assertEquals("PRONOUN_ADJECTIVE", updated.getType().toString());
    assertNotNull(updated.getDeclinationMatrix());
  }

  @Test
  @TestSecurity(user = "user", roles = "words#update")
  public void shouldUpdateWordWithDeclinationMatrixWhenTypeIsNumeralAdjective() throws Exception {
    WordEntity word = WordMother.random().id(null).type(WordType.NOUN).build();
    WordModel saved = persist(WordModel.fromEntity(word));

    UpdateWordRequest dto = new UpdateWordRequest(
      "newRussian", "newSpanish", "newEnglish", "NUMERAL_ADJECTIVE", null, new UpdateWordDeclinationMatrixDto(
      "nominativeMasculine",
      "nominativeFeminine",
      "nominativeNeuter",
      "nominativePlural",
      "accusativeMasculine",
      "accusativeFeminine",
      "accusativeNeuter",
      "accusativePlural",
      "genitiveMasculine",
      "genitiveFeminine",
      "genitiveNeuter",
      "genitivePlural",
      "dativeMasculine",
      "dativeFeminine",
      "dativeNeuter",
      "dativePlural",
      "instrumentalMasculine",
      "instrumentalFeminine",
      "instrumentalNeuter",
      "instrumentalPlural",
      "prepositionalMasculine",
      "prepositionalFeminine",
      "prepositionalNeuter",
      "prepositionalPlural"
    ), null
    );
    String json = objectMapper.writeValueAsString(dto);

    given().body(json).contentType("application/json").when().put("/words/" + saved.getId().toString()).then().statusCode(200);

    WordModel updated = em.find(WordModel.class, saved.getId());
    assertNotNull(updated);
    assertEquals("NUMERAL_ADJECTIVE", updated.getType().toString());
    assertNotNull(updated.getDeclinationMatrix());
  }

  @Test
  @TestSecurity(user = "user", roles = "words#update")
  public void shouldReturn404IfWordNotFound() throws Exception {
    UpdateWordRequest dto = new UpdateWordRequest("newRussian", "newSpanish", "newEnglish", "OTHER", null, null, null);
    String json = objectMapper.writeValueAsString(dto);

    given().body(json).contentType("application/json").when().put("/words/" + UUID.randomUUID().toString()).then().statusCode(404);
  }

  @Test
  public void shouldReturn401IfUserIsNotAuthenticated() throws Exception {
    UpdateWordRequest dto = new UpdateWordRequest("newRussian", "newSpanish", "newEnglish", "OTHER", null, null, null);
    String json = objectMapper.writeValueAsString(dto);

    given().body(json).contentType("application/json").when().put("/words/" + UUID.randomUUID().toString()).then().statusCode(401);
  }

  @Test
  @TestSecurity(user = "user")
  public void shouldReturn403IfUserIsNotAuthorized() throws Exception {
    UpdateWordRequest dto = new UpdateWordRequest("newRussian", "newSpanish", "newEnglish", "OTHER", null, null, null);
    String json = objectMapper.writeValueAsString(dto);

    given().body(json).contentType("application/json").when().put("/words/" + UUID.randomUUID().toString()).then().statusCode(403);
  }

}
