package es.pausegarra.russian_cheatsheet.integration.vocabulary;

import com.fasterxml.jackson.core.JsonProcessingException;
import es.pausegarra.russian_cheatsheet.annotations.IntegrationTest;
import es.pausegarra.russian_cheatsheet.mother.words.entities.WordCaseEntityMother;
import es.pausegarra.russian_cheatsheet.mother.words.requests.UpdateWordRequestMother;
import es.pausegarra.russian_cheatsheet.mother.words.entities.VerbConjugationEntityMother;
import es.pausegarra.russian_cheatsheet.mother.words.requests.WordCasesRequestMother;
import es.pausegarra.russian_cheatsheet.mother.words.requests.WordConjugationRequestMother;
import es.pausegarra.russian_cheatsheet.mother.words.entities.WordEntityMother;
import es.pausegarra.russian_cheatsheet.vocabulary.domain.entities.VerbConjugationEntity;
import es.pausegarra.russian_cheatsheet.vocabulary.domain.entities.WordCasesEntity;
import es.pausegarra.russian_cheatsheet.vocabulary.domain.entities.WordEntity;
import es.pausegarra.russian_cheatsheet.vocabulary.domain.enums.WordTypes;
import es.pausegarra.russian_cheatsheet.vocabulary.infrastructure.requests.UpdateWordRequest;
import es.pausegarra.russian_cheatsheet.vocabulary.infrastructure.requests.WordCasesRequest;
import es.pausegarra.russian_cheatsheet.vocabulary.infrastructure.requests.WordRequestConjugations;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.smallrye.common.constraint.Assert.assertNotNull;
import static io.smallrye.common.constraint.Assert.assertTrue;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@QuarkusTest
public class UpdateWordTest extends IntegrationTest {

  @Transactional
  public WordEntity setUp() {
    WordEntity word = WordEntityMother.random()
      .type(WordTypes.ADVERB)
      .id(null)
      .build();
    em.persist(word);
    em.flush();
    em.clear();

    return word;
  }

  @Transactional
  public WordEntity setUpWithConjugations() {
    WordEntity word = WordEntityMother.random()
      .id(null)
      .build();
    em.persist(word);

    VerbConjugationEntity conjugations = VerbConjugationEntityMother.random()
      .id(null)
      .word(word)
      .build();
    em.persist(conjugations);

    em.flush();
    em.clear();

    return word;
  }

  @Transactional
  public WordEntity setUpWithCases() {
    WordEntity word = WordEntityMother.random()
      .type(WordTypes.NOUN)
      .id(null)
      .build();
    em.persist(word);

    WordCasesEntity cases = WordCaseEntityMother.random()
      .id(null)
      .word(word)
      .build();
    em.persist(cases);

    em.flush();
    em.clear();

    return word;
  }

  @Test
  @TestSecurity(
    user = "user", roles = "words#update"
  )
  public void shouldUpdateWord() throws JsonProcessingException {
    WordEntity word = setUp();

    UpdateWordRequest request = UpdateWordRequestMother.random()
      .type(WordTypes.ADVERB.toString())
      .build();
    String json = objectMapper.writeValueAsString(request);
    given().body(json)
      .contentType("application/json")
      .when()
      .put("/words/" + word.getId())
      .then()
      .statusCode(200);

    WordEntity updated = em.find(WordEntity.class, word.getId());
    assertNotNull(updated);
    assertTrue(updated.getRussian()
      .equals(request.russian()));
    assertTrue(updated.getEnglish()
      .equals(request.english()));
    assertTrue(updated.getSpanish()
      .equals(request.spanish()));
    assertTrue(updated.getType()
      .equals(WordTypes.ADVERB));
  }

  @Test
  @TestSecurity(
    user = "user", roles = "words#update"
  )
  public void shouldUpdateWordThatHasNoConjugationsWithConjugations() throws JsonProcessingException {
    WordEntity word = setUp();

    WordRequestConjugations conjugations = WordConjugationRequestMother.random()
      .build();
    UpdateWordRequest request = UpdateWordRequestMother.random()
      .type(WordTypes.VERB.toString())
      .conjugations(conjugations)
      .build();
    String json = objectMapper.writeValueAsString(request);
    given().body(json)
      .contentType("application/json")
      .when()
      .put("/words/" + word.getId())
      .then()
      .statusCode(200);

    WordEntity updated = em.find(WordEntity.class, word.getId());
    assertNotNull(updated);
    assertTrue(updated.getRussian()
      .equals(request.russian()));
    assertTrue(updated.getEnglish()
      .equals(request.english()));
    assertTrue(updated.getSpanish()
      .equals(request.spanish()));
    assertTrue(updated.getType()
      .equals(WordTypes.VERB));
    assertNotNull(updated.getConjugations());
  }

  @Test
  @TestSecurity(
    user = "user", roles = "words#update"
  )
  public void shouldUpdateWordThatHasConjugationsWithNoConjugations() throws JsonProcessingException {
    WordEntity word = setUpWithConjugations();

    UpdateWordRequest request = UpdateWordRequestMother.random()
      .type(WordTypes.ADVERB.toString())
      .build();
    String json = objectMapper.writeValueAsString(request);
    given().body(json)
      .contentType("application/json")
      .when()
      .put("/words/" + word.getId())
      .then()
      .statusCode(200);

    WordEntity updated = em.find(WordEntity.class, word.getId());
    assertNotNull(updated);
    assertTrue(updated.getRussian()
      .equals(request.russian()));
    assertTrue(updated.getEnglish()
      .equals(request.english()));
    assertTrue(updated.getSpanish()
      .equals(request.spanish()));
    assertTrue(updated.getType()
      .equals(WordTypes.ADVERB));
    assertNull(updated.getConjugations());
  }

  @Test
  @TestSecurity(
    user = "user", roles = "words#update"
  )
  public void shouldUpdateWordThatHasConjugationsWithConjugations() throws JsonProcessingException {
    WordEntity word = setUpWithConjugations();

    WordRequestConjugations conjugations = WordConjugationRequestMother.random()
      .build();
    UpdateWordRequest request = UpdateWordRequestMother.random()
      .type(WordTypes.VERB.toString())
      .conjugations(conjugations)
      .build();
    String json = objectMapper.writeValueAsString(request);
    given().body(json)
      .contentType("application/json")
      .when()
      .put("/words/" + word.getId())
      .then()
      .statusCode(200);

    WordEntity updated = em.find(WordEntity.class, word.getId());
    assertNotNull(updated);
    assertTrue(updated.getRussian()
      .equals(request.russian()));
    assertTrue(updated.getEnglish()
      .equals(request.english()));
    assertTrue(updated.getSpanish()
      .equals(request.spanish()));
    assertTrue(updated.getType()
      .equals(WordTypes.VERB));
    assertNotNull(updated.getConjugations());
    assertEquals(
      conjugations.imperfectivePresentFirstPersonSingular(),
      updated.getConjugations()
        .getImperfectivePresentFirstPersonSingular()
    );
  }

  @Test
  @TestSecurity(
    user = "user", roles = "words#update"
  )
  public void shouldReturn400WhenDataIsInvalid() throws JsonProcessingException {
    WordEntity word = setUp();

    UpdateWordRequest request = UpdateWordRequestMother.random()
      .russian(null)
      .english(null)
      .spanish(null)
      .type("VERB")
      .build();
    String json = objectMapper.writeValueAsString(request);
    given().body(json)
      .when()
      .contentType("application/json")
      .put("/words/" + word.getId())
      .then()
      .statusCode(400)
      .body("errors.size()", is(3))
      .body(
        "errors.field",
        hasItems("handle.dto.russian", "handle.dto.english", "handle.dto.spanish")
      )
      .body("errors.message", hasItem("must not be blank"));
  }

  @Test
  public void shouldReturn401WhenUserIsNotAuthenticated() throws JsonProcessingException {
    WordEntity word = setUp();

    UpdateWordRequest request = UpdateWordRequestMother.random()
      .build();
    String json = objectMapper.writeValueAsString(request);
    given().body(json)
      .when()
      .put("/words/" + word.getId())
      .then()
      .statusCode(401);
  }

  @Test
  @TestSecurity(user = "test")
  public void shouldReturn403WhenUserDoesNotHavePermissions() throws JsonProcessingException {
    WordEntity word = setUp();

    UpdateWordRequest request = UpdateWordRequestMother.random()
      .build();
    String json = objectMapper.writeValueAsString(request);
    given().body(json)
      .when()
      .put("/words/" + word.getId())
      .then()
      .statusCode(403);
  }

  @Test
  @TestSecurity(
    user = "user", roles = "words#update"
  )
  public void shouldUpdateWordWithoutCasesAndAddCases() throws JsonProcessingException {
    WordEntity word = setUp();

    WordCasesRequest casesRequest = WordCasesRequestMother.random()
      .build();
    UpdateWordRequest request = UpdateWordRequestMother.random()
      .type(WordTypes.NOUN.toString())
      .cases(casesRequest)
      .build();
    String json = objectMapper.writeValueAsString(request);

    given().body(json)
      .contentType("application/json")
      .when()
      .put("/words/" + word.getId())
      .then()
      .statusCode(200);

    WordEntity updated = em.find(WordEntity.class, word.getId());
    assertNotNull(updated);
    assertTrue(updated.getRussian()
      .equals(request.russian()));
    assertTrue(updated.getEnglish()
      .equals(request.english()));
    assertTrue(updated.getSpanish()
      .equals(request.spanish()));
    assertTrue(updated.getType()
      .equals(WordTypes.NOUN));
    assertNotNull(updated.getCases());
    assertEquals(casesRequest.nominative(), updated.getCases()
      .getNominative());
    assertEquals(casesRequest.genitive(), updated.getCases()
      .getGenitive());
    assertEquals(casesRequest.dative(), updated.getCases()
      .getDative());
    assertEquals(casesRequest.accusative(), updated.getCases()
      .getAccusative());
    assertEquals(casesRequest.instrumental(), updated.getCases()
      .getInstrumental());
    assertEquals(casesRequest.prepositional(), updated.getCases()
      .getPrepositional());
  }

  @Test
  @TestSecurity(
    user = "user", roles = "words#update"
  )
  public void shouldUpdateWordWithCasesAndUpdateCases() throws JsonProcessingException {
    WordEntity word = setUpWithCases();

    WordCasesRequest casesRequest = WordCasesRequestMother.random()
      .build();
    UpdateWordRequest request = UpdateWordRequestMother.random()
      .type(WordTypes.NOUN.toString())
      .cases(casesRequest)
      .build();
    String json = objectMapper.writeValueAsString(request);

    given().body(json)
      .contentType("application/json")
      .when()
      .put("/words/" + word.getId())
      .then()
      .statusCode(200);

    WordEntity updated = em.find(WordEntity.class, word.getId());
    assertNotNull(updated);
    assertTrue(updated.getRussian()
      .equals(request.russian()));
    assertTrue(updated.getEnglish()
      .equals(request.english()));
    assertTrue(updated.getSpanish()
      .equals(request.spanish()));
    assertTrue(updated.getType()
      .equals(WordTypes.NOUN));
    assertNotNull(updated.getCases());
    assertEquals(casesRequest.nominative(), updated.getCases()
      .getNominative());
    assertEquals(casesRequest.genitive(), updated.getCases()
      .getGenitive());
    assertEquals(casesRequest.dative(), updated.getCases()
      .getDative());
    assertEquals(casesRequest.accusative(), updated.getCases()
      .getAccusative());
    assertEquals(casesRequest.instrumental(), updated.getCases()
      .getInstrumental());
    assertEquals(casesRequest.prepositional(), updated.getCases()
      .getPrepositional());
  }

  @Test
  @TestSecurity(
    user = "user", roles = "words#update"
  )
  public void shouldUpdateWordWithCasesAndRemoveCases() throws JsonProcessingException {
    WordEntity word = setUpWithCases();

    UpdateWordRequest request = UpdateWordRequestMother.random()
      .type(WordTypes.VERB.toString())
      .build();
    String json = objectMapper.writeValueAsString(request);

    given().body(json)
      .contentType("application/json")
      .when()
      .put("/words/" + word.getId())
      .then()
      .statusCode(200);

    WordEntity updated = em.find(WordEntity.class, word.getId());
    assertNotNull(updated);
    assertTrue(updated.getRussian()
      .equals(request.russian()));
    assertTrue(updated.getEnglish()
      .equals(request.english()));
    assertTrue(updated.getSpanish()
      .equals(request.spanish()));
    assertTrue(updated.getType()
      .equals(WordTypes.VERB));
    assertNull(updated.getCases());
  }

}
