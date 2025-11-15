package es.pausegarra.russian_cheatsheet.integration.words;

import es.pausegarra.russian_cheatsheet.base.IntegrationTest;
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
      "perfectiveFutureThirdPersonPlural"
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
    assertEquals(
      dto.conjugations().imperfectivePresentFirstPersonSingular(),
      updated.getConjugations().getImperfectivePresentFirstPersonSingular()
    );
    assertEquals(
      dto.conjugations().imperfectivePresentSecondPersonSingular(),
      updated.getConjugations().getImperfectivePresentSecondPersonSingular()
    );
    assertEquals(
      dto.conjugations().imperfectivePresentThirdPersonSingular(),
      updated.getConjugations().getImperfectivePresentThirdPersonSingular()
    );
    assertEquals(
      dto.conjugations().imperfectivePresentFirstPersonPlural(),
      updated.getConjugations().getImperfectivePresentFirstPersonPlural()
    );
    assertEquals(
      dto.conjugations().imperfectivePresentSecondPersonPlural(),
      updated.getConjugations().getImperfectivePresentSecondPersonPlural()
    );
    assertEquals(
      dto.conjugations().imperfectivePresentThirdPersonPlural(),
      updated.getConjugations().getImperfectivePresentThirdPersonPlural()
    );
    assertEquals(dto.conjugations().imperfectivePastMasculine(), updated.getConjugations().getImperfectivePastMasculine());
    assertEquals(dto.conjugations().imperfectivePastFeminine(), updated.getConjugations().getImperfectivePastFeminine());
    assertEquals(dto.conjugations().imperfectivePastNeuter(), updated.getConjugations().getImperfectivePastNeuter());
    assertEquals(dto.conjugations().imperfectivePastPlural(), updated.getConjugations().getImperfectivePastPlural());
    assertEquals(
      dto.conjugations().imperfectiveFutureFirstPersonSingular(),
      updated.getConjugations().getImperfectiveFutureFirstPersonSingular()
    );
    assertEquals(
      dto.conjugations().imperfectiveFutureSecondPersonSingular(),
      updated.getConjugations().getImperfectiveFutureSecondPersonSingular()
    );
    assertEquals(
      dto.conjugations().imperfectiveFutureThirdPersonSingular(),
      updated.getConjugations().getImperfectiveFutureThirdPersonSingular()
    );
    assertEquals(
      dto.conjugations().imperfectiveFutureFirstPersonPlural(),
      updated.getConjugations().getImperfectiveFutureFirstPersonPlural()
    );
    assertEquals(
      dto.conjugations().imperfectiveFutureSecondPersonPlural(),
      updated.getConjugations().getImperfectiveFutureSecondPersonPlural()
    );
    assertEquals(
      dto.conjugations().imperfectiveFutureThirdPersonPlural(),
      updated.getConjugations().getImperfectiveFutureThirdPersonPlural()
    );
    assertEquals(dto.conjugations().perfectivePastMasculine(), updated.getConjugations().getPerfectivePastMasculine());
    assertEquals(dto.conjugations().perfectivePastFeminine(), updated.getConjugations().getPerfectivePastFeminine());
    assertEquals(dto.conjugations().perfectivePastNeuter(), updated.getConjugations().getPerfectivePastNeuter());
    assertEquals(dto.conjugations().perfectivePastPlural(), updated.getConjugations().getPerfectivePastPlural());
    assertEquals(
      dto.conjugations().perfectiveFutureFirstPersonSingular(),
      updated.getConjugations().getPerfectiveFutureFirstPersonSingular()
    );
    assertEquals(
      dto.conjugations().perfectiveFutureSecondPersonSingular(),
      updated.getConjugations().getPerfectiveFutureSecondPersonSingular()
    );
    assertEquals(
      dto.conjugations().perfectiveFutureThirdPersonSingular(),
      updated.getConjugations().getPerfectiveFutureThirdPersonSingular()
    );
    assertEquals(dto.conjugations().perfectiveFutureFirstPersonPlural(), updated.getConjugations().getPerfectiveFutureFirstPersonPlural());
    assertEquals(
      dto.conjugations().perfectiveFutureSecondPersonPlural(),
      updated.getConjugations().getPerfectiveFutureSecondPersonPlural()
    );
    assertEquals(dto.conjugations().perfectiveFutureThirdPersonPlural(), updated.getConjugations().getPerfectiveFutureThirdPersonPlural());
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
      new UpdateWordDeclinationDto("nominative", "accusative", "genitive", "dative", "instrumental", "prepositional"),
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
    assertEquals(dto.declinations().nominative(), updated.getDeclinations().getNominative());
    assertEquals(dto.declinations().accusative(), updated.getDeclinations().getAccusative());
    assertEquals(dto.declinations().genitive(), updated.getDeclinations().getGenitive());
    assertEquals(dto.declinations().dative(), updated.getDeclinations().getDative());
    assertEquals(dto.declinations().instrumental(), updated.getDeclinations().getInstrumental());
    assertEquals(dto.declinations().prepositional(), updated.getDeclinations().getPrepositional());
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
    assertEquals(dto.declinationMatrix().nominativeMasculine(), updated.getDeclinationMatrix().getNominativeMasculine());
    assertEquals(dto.declinationMatrix().nominativeFeminine(), updated.getDeclinationMatrix().getNominativeFeminine());
    assertEquals(dto.declinationMatrix().nominativeNeuter(), updated.getDeclinationMatrix().getNominativeNeuter());
    assertEquals(dto.declinationMatrix().nominativePlural(), updated.getDeclinationMatrix().getNominativePlural());
    assertEquals(dto.declinationMatrix().accusativeMasculine(), updated.getDeclinationMatrix().getAccusativeMasculine());
    assertEquals(dto.declinationMatrix().accusativeFeminine(), updated.getDeclinationMatrix().getAccusativeFeminine());
    assertEquals(dto.declinationMatrix().accusativeNeuter(), updated.getDeclinationMatrix().getAccusativeNeuter());
    assertEquals(dto.declinationMatrix().accusativePlural(), updated.getDeclinationMatrix().getAccusativePlural());
    assertEquals(dto.declinationMatrix().genitiveMasculine(), updated.getDeclinationMatrix().getGenitiveMasculine());
    assertEquals(dto.declinationMatrix().genitiveFeminine(), updated.getDeclinationMatrix().getGenitiveFeminine());
    assertEquals(dto.declinationMatrix().genitiveNeuter(), updated.getDeclinationMatrix().getGenitiveNeuter());
    assertEquals(dto.declinationMatrix().genitivePlural(), updated.getDeclinationMatrix().getGenitivePlural());
    assertEquals(dto.declinationMatrix().dativeMasculine(), updated.getDeclinationMatrix().getDativeMasculine());
    assertEquals(dto.declinationMatrix().dativeFeminine(), updated.getDeclinationMatrix().getDativeFeminine());
    assertEquals(dto.declinationMatrix().dativeNeuter(), updated.getDeclinationMatrix().getDativeNeuter());
    assertEquals(dto.declinationMatrix().dativePlural(), updated.getDeclinationMatrix().getDativePlural());
    assertEquals(dto.declinationMatrix().instrumentalMasculine(), updated.getDeclinationMatrix().getInstrumentalMasculine());
    assertEquals(dto.declinationMatrix().instrumentalFeminine(), updated.getDeclinationMatrix().getInstrumentalFeminine());
    assertEquals(dto.declinationMatrix().instrumentalNeuter(), updated.getDeclinationMatrix().getInstrumentalNeuter());
    assertEquals(dto.declinationMatrix().instrumentalPlural(), updated.getDeclinationMatrix().getInstrumentalPlural());
    assertEquals(dto.declinationMatrix().prepositionalMasculine(), updated.getDeclinationMatrix().getPrepositionalMasculine());
    assertEquals(dto.declinationMatrix().prepositionalFeminine(), updated.getDeclinationMatrix().getPrepositionalFeminine());
    assertEquals(dto.declinationMatrix().prepositionalNeuter(), updated.getDeclinationMatrix().getPrepositionalNeuter());
    assertEquals(dto.declinationMatrix().prepositionalPlural(), updated.getDeclinationMatrix().getPrepositionalPlural());
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
