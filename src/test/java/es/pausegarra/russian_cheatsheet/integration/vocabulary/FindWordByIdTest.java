package es.pausegarra.russian_cheatsheet.integration.vocabulary;

import es.pausegarra.russian_cheatsheet.annotations.IntegrationTest;
import es.pausegarra.russian_cheatsheet.mother.VerbConjugationEntityMother;
import es.pausegarra.russian_cheatsheet.mother.WordCaseMother;
import es.pausegarra.russian_cheatsheet.mother.WordEntityMother;
import es.pausegarra.russian_cheatsheet.vocabulary.domain.entities.VerbConjugationEntity;
import es.pausegarra.russian_cheatsheet.vocabulary.domain.entities.WordCasesEntity;
import es.pausegarra.russian_cheatsheet.vocabulary.domain.entities.WordEntity;
import es.pausegarra.russian_cheatsheet.vocabulary.domain.enums.WordTypes;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

@QuarkusTest
public class FindWordByIdTest extends IntegrationTest {

  @Transactional
  public WordEntity setUpWithConjugations() {
    WordEntity word = WordEntityMother.random()
      .id(null)
      .build();
    em.persist(word);

    VerbConjugationEntity conjugation = VerbConjugationEntityMother.random()
      .id(null)
      .word(word)
      .build();
    word = word.withConjugations(conjugation);
    word = em.merge(word);

    em.flush();

    return word;
  }

  @Transactional
  public WordEntity setUpWithoutConjugations() {
    WordEntity word = WordEntityMother.random()
      .type(WordTypes.NOUN)
      .id(null)
      .build();
    em.persist(word);
    return word;
  }

  @Transactional
  public WordEntity setUpWithCases() {
    WordEntity word = WordEntityMother.random()
      .id(null)
      .build();
    em.persist(word);

    WordCasesEntity cases = WordCaseMother.random()
      .id(null)
      .word(word)
      .build();
    word = word.withCases(cases);

    em.persist(cases);

    return word;
  }

  @Test
  public void shouldReturnWord() {
    WordEntity word = setUpWithConjugations();

    given().when()
      .get("/words/" + word.getId())
      .then()
      .statusCode(200)
      .body(
        "id",
        is(word.getId()
          .toString())
      )
      .body("russian", is(word.getRussian()))
      .body("english", is(word.getEnglish()))
      .body("spanish", is(word.getSpanish()))
      .body(
        "type",
        is(word.getType()
          .toString())
      )
      .body(
        "conjugations.imperfectivePresentFirstPersonSingular",
        is(word.getConjugations()
          .getImperfectivePresentFirstPersonSingular())
      )
      .body(
        "conjugations.imperfectivePresentSecondPersonSingular",
        is(word.getConjugations()
          .getImperfectivePresentSecondPersonSingular())
      )
      .body(
        "conjugations.imperfectivePresentThirdPersonSingular",
        is(word.getConjugations()
          .getImperfectivePresentThirdPersonSingular())
      )
      .body(
        "conjugations.imperfectivePresentFirstPersonPlural",
        is(word.getConjugations()
          .getImperfectivePresentFirstPersonPlural())
      )
      .body(
        "conjugations.imperfectivePresentSecondPersonPlural",
        is(word.getConjugations()
          .getImperfectivePresentSecondPersonPlural())
      )
      .body(
        "conjugations.imperfectivePresentThirdPersonPlural",
        is(word.getConjugations()
          .getImperfectivePresentThirdPersonPlural())
      )
      .body(
        "conjugations.imperfectivePastMasculine",
        is(word.getConjugations()
          .getImperfectivePastMasculine())
      )
      .body(
        "conjugations.imperfectivePastFeminine",
        is(word.getConjugations()
          .getImperfectivePastFeminine())
      )
      .body(
        "conjugations.imperfectivePastNeuter",
        is(word.getConjugations()
          .getImperfectivePastNeuter())
      )
      .body(
        "conjugations.imperfectivePastPlural",
        is(word.getConjugations()
          .getImperfectivePastPlural())
      )
      .body(
        "conjugations.imperfectiveFutureFirstPersonSingular",
        is(word.getConjugations()
          .getImperfectiveFutureFirstPersonSingular())
      )
      .body(
        "conjugations.imperfectiveFutureSecondPersonSingular",
        is(word.getConjugations()
          .getImperfectiveFutureSecondPersonSingular())
      )
      .body(
        "conjugations.imperfectiveFutureThirdPersonSingular",
        is(word.getConjugations()
          .getImperfectiveFutureThirdPersonSingular())
      )
      .body(
        "conjugations.imperfectiveFutureFirstPersonPlural",
        is(word.getConjugations()
          .getImperfectiveFutureFirstPersonPlural())
      )
      .body(
        "conjugations.imperfectiveFutureSecondPersonPlural",
        is(word.getConjugations()
          .getImperfectiveFutureSecondPersonPlural())
      )
      .body(
        "conjugations.imperfectiveFutureThirdPersonPlural",
        is(word.getConjugations()
          .getImperfectiveFutureThirdPersonPlural())
      )
      .body(
        "conjugations.perfectivePresentFirstPersonSingular",
        is(word.getConjugations()
          .getPerfectivePresentFirstPersonSingular())
      )
      .body(
        "conjugations.perfectivePresentSecondPersonSingular",
        is(word.getConjugations()
          .getPerfectivePresentSecondPersonSingular())
      )
      .body(
        "conjugations.perfectivePresentThirdPersonSingular",
        is(word.getConjugations()
          .getPerfectivePresentThirdPersonSingular())
      )
      .body(
        "conjugations.perfectivePresentFirstPersonPlural",
        is(word.getConjugations()
          .getPerfectivePresentFirstPersonPlural())
      )
      .body(
        "conjugations.perfectivePresentSecondPersonPlural",
        is(word.getConjugations()
          .getPerfectivePresentSecondPersonPlural())
      )
      .body(
        "conjugations.perfectivePresentThirdPersonPlural",
        is(word.getConjugations()
          .getPerfectivePresentThirdPersonPlural())
      )
      .body(
        "conjugations.perfectivePastMasculine",
        is(word.getConjugations()
          .getPerfectivePastMasculine())
      )
      .body(
        "conjugations.perfectivePastFeminine",
        is(word.getConjugations()
          .getPerfectivePastFeminine())
      )
      .body(
        "conjugations.perfectivePastNeuter",
        is(word.getConjugations()
          .getPerfectivePastNeuter())
      )
      .body(
        "conjugations.perfectivePastPlural",
        is(word.getConjugations()
          .getPerfectivePastPlural())
      )
      .body(
        "conjugations.perfectiveFutureFirstPersonSingular",
        is(word.getConjugations()
          .getPerfectiveFutureFirstPersonSingular())
      )
      .body(
        "conjugations.perfectiveFutureSecondPersonSingular",
        is(word.getConjugations()
          .getPerfectiveFutureSecondPersonSingular())
      )
      .body(
        "conjugations.perfectiveFutureThirdPersonSingular",
        is(word.getConjugations()
          .getPerfectiveFutureThirdPersonSingular())
      )
      .body(
        "conjugations.perfectiveFutureFirstPersonPlural",
        is(word.getConjugations()
          .getPerfectiveFutureFirstPersonPlural())
      )
      .body(
        "conjugations.perfectiveFutureSecondPersonPlural",
        is(word.getConjugations()
          .getPerfectiveFutureSecondPersonPlural())
      )
      .body(
        "conjugations.perfectiveFutureThirdPersonPlural",
        is(word.getConjugations()
          .getPerfectiveFutureThirdPersonPlural())
      );
  }

  @Test
  public void shouldReturn404WhenWordIsNotFound() {
    given().when()
      .get("/words/" + UUID.randomUUID())
      .then()
      .statusCode(404);
  }

  @Test
  public void shouldWotkWithNoConjugations() {
    WordEntity word = setUpWithoutConjugations();

    given().when()
      .get("/words/" + word.getId())
      .then()
      .statusCode(200)
      .body(
        "id",
        is(word.getId()
          .toString())
      )
      .body("russian", is(word.getRussian()))
      .body("english", is(word.getEnglish()))
      .body("spanish", is(word.getSpanish()))
      .body(
        "type",
        is(word.getType()
          .toString())
      )
      .body("conjugations", nullValue());
  }

  @Test
  public void shouldReturnWordWithCases() {
    WordEntity word = setUpWithCases();

    given().when()
      .get("/words/" + word.getId())
      .then()
      .statusCode(200)
      .body(
        "id",
        is(word.getId()
          .toString())
      )
      .body("russian", is(word.getRussian()))
      .body("english", is(word.getEnglish()))
      .body("spanish", is(word.getSpanish()))
      .body(
        "type",
        is(word.getType()
          .toString())
      )
      .body(
        "cases.nominativeMasculine",
        is(word.getCases()
          .getNominativeMasculine())
      )
      .body(
        "cases.nominativeFeminine",
        is(word.getCases()
          .getNominativeFeminine())
      )
      .body(
        "cases.nominativeNeuter",
        is(word.getCases()
          .getNominativeNeuter())
      )
      .body(
        "cases.genitiveMasculine",
        is(word.getCases()
          .getGenitiveMasculine())
      )
      .body(
        "cases.genitiveFeminine",
        is(word.getCases()
          .getGenitiveFeminine())
      )
      .body(
        "cases.genitiveNeuter",
        is(word.getCases()
          .getGenitiveNeuter())
      )
      .body(
        "cases.dativeMasculine",
        is(word.getCases()
          .getDativeMasculine())
      )
      .body(
        "cases.dativeFeminine",
        is(word.getCases()
          .getDativeFeminine())
      )
      .body(
        "cases.dativeNeuter",
        is(word.getCases()
          .getDativeNeuter())
      )
      .body(
        "cases.accusativeMasculine",
        is(word.getCases()
          .getAccusativeMasculine())
      )
      .body(
        "cases.accusativeFeminine",
        is(word.getCases()
          .getAccusativeFeminine())
      )
      .body(
        "cases.accusativeNeuter",
        is(word.getCases()
          .getAccusativeNeuter())
      )
      .body(
        "cases.instrumentalMasculine",
        is(word.getCases()
          .getInstrumentalMasculine())
      )
      .body(
        "cases.instrumentalFeminine",
        is(word.getCases()
          .getInstrumentalFeminine())
      )
      .body(
        "cases.instrumentalNeuter",
        is(word.getCases()
          .getInstrumentalNeuter())
      )
      .body(
        "cases.prepositionalMasculine",
        is(word.getCases()
          .getPrepositionalMasculine())
      )
      .body(
        "cases.prepositionalFeminine",
        is(word.getCases()
          .getPrepositionalFeminine())
      )
      .body(
        "cases.prepositionalNeuter",
        is(word.getCases()
          .getPrepositionalNeuter())
      );
  }

}
