package es.pausegarra.russian_cheatsheet.integration.vocabulary;

import es.pausegarra.russian_cheatsheet.annotations.IntegrationTest;
import es.pausegarra.russian_cheatsheet.vocabulary.domain.entities.VerbConjugationEntity;
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
    WordEntity word = WordEntity.create(null, "a", "a", "a", WordTypes.VERB);
    em.persist(word);

    VerbConjugationEntity conjugation = VerbConjugationEntity.create(
      null, word,
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
      "perfectivePresentFirstPersonSingular",
      "perfectivePresentSecondPersonSingular",
      "perfectivePresentThirdPersonSingular",
      "perfectivePresentFirstPersonPlural",
      "perfectivePresentSecondPersonPlural",
      "perfectivePresentThirdPersonPlural",
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
    );
    word = word.withConjugations(conjugation);
    word = em.merge(word);

    em.flush();

    return word;
  }

  @Transactional
  public WordEntity setUpWithoutConjugations() {
    WordEntity word = WordEntity.create(null, "a", "a", "a", WordTypes.NOUN);
    em.persist(word);
    return word;
  }

  @Test
  public void shouldReturnWord() {
    WordEntity word = setUpWithConjugations();

    given().when()
      .get("/words/" + word.getId())
      .then()
      .statusCode(200)
      .body("id", is(word.getId().toString()))
      .body("russian", is("a"))
      .body("english", is("a"))
      .body("spanish", is("a"))
      .body("type", is("VERB"))
      .body("conjugations.imperfectivePresentFirstPersonSingular", is("imperfectivePresentFirstPersonSingular"))
      .body("conjugations.imperfectivePresentSecondPersonSingular", is("imperfectivePresentSecondPersonSingular"))
      .body("conjugations.imperfectivePresentThirdPersonSingular", is("imperfectivePresentThirdPersonSingular"))
      .body("conjugations.imperfectivePresentFirstPersonPlural", is("imperfectivePresentFirstPersonPlural"))
      .body("conjugations.imperfectivePresentSecondPersonPlural", is("imperfectivePresentSecondPersonPlural"))
      .body("conjugations.imperfectivePresentThirdPersonPlural", is("imperfectivePresentThirdPersonPlural"))
      .body("conjugations.imperfectivePastMasculine", is("imperfectivePastMasculine"))
      .body("conjugations.imperfectivePastFeminine", is("imperfectivePastFeminine"))
      .body("conjugations.imperfectivePastNeuter", is("imperfectivePastNeuter"))
      .body("conjugations.imperfectivePastPlural", is("imperfectivePastPlural"))
      .body("conjugations.imperfectiveFutureFirstPersonSingular", is("imperfectiveFutureFirstPersonSingular"))
      .body("conjugations.imperfectiveFutureSecondPersonSingular", is("imperfectiveFutureSecondPersonSingular"))
      .body("conjugations.imperfectiveFutureThirdPersonSingular", is("imperfectiveFutureThirdPersonSingular"))
      .body("conjugations.imperfectiveFutureFirstPersonPlural", is("imperfectiveFutureFirstPersonPlural"))
      .body("conjugations.imperfectiveFutureSecondPersonPlural", is("imperfectiveFutureSecondPersonPlural"))
      .body("conjugations.imperfectiveFutureThirdPersonPlural", is("imperfectiveFutureThirdPersonPlural"))
      .body("conjugations.perfectivePresentFirstPersonSingular", is("perfectivePresentFirstPersonSingular"))
      .body("conjugations.perfectivePresentSecondPersonSingular", is("perfectivePresentSecondPersonSingular"))
      .body("conjugations.perfectivePresentThirdPersonSingular", is("perfectivePresentThirdPersonSingular"))
      .body("conjugations.perfectivePresentFirstPersonPlural", is("perfectivePresentFirstPersonPlural"))
      .body("conjugations.perfectivePresentSecondPersonPlural", is("perfectivePresentSecondPersonPlural"))
      .body("conjugations.perfectivePresentThirdPersonPlural", is("perfectivePresentThirdPersonPlural"))
      .body("conjugations.perfectivePastMasculine", is("perfectivePastMasculine"))
      .body("conjugations.perfectivePastFeminine", is("perfectivePastFeminine"))
      .body("conjugations.perfectivePastNeuter", is("perfectivePastNeuter"))
      .body("conjugations.perfectivePastPlural", is("perfectivePastPlural"))
      .body("conjugations.perfectiveFutureFirstPersonSingular", is("perfectiveFutureFirstPersonSingular"))
      .body("conjugations.perfectiveFutureSecondPersonSingular", is("perfectiveFutureSecondPersonSingular"))
      .body("conjugations.perfectiveFutureThirdPersonSingular", is("perfectiveFutureThirdPersonSingular"))
      .body("conjugations.perfectiveFutureFirstPersonPlural", is("perfectiveFutureFirstPersonPlural"))
      .body("conjugations.perfectiveFutureSecondPersonPlural", is("perfectiveFutureSecondPersonPlural"))
      .body("conjugations.perfectiveFutureThirdPersonPlural", is("perfectiveFutureThirdPersonPlural"));
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
      .body("id", is(word.getId().toString()))
      .body("russian", is("a"))
      .body("english", is("a"))
      .body("spanish", is("a"))
      .body("type", is("NOUN"))
      .body("conjugations", nullValue());
  }

}
