package es.pausegarra.russkiy_po_moyemu.integration.alphabet;

import es.pausegarra.russkiy_po_moyemu.alphabet.domain.entities.LetterEntity;
import es.pausegarra.russkiy_po_moyemu.annotations.IntegrationTest;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

@QuarkusTest
public class ListLetterTest extends IntegrationTest {

  @Test
  @TestTransaction
  public void shouldReturnAllLetters() {
    LetterEntity letter = LetterEntity.create(
        null,
        "a",
        "a",
        "a"
    );
    em.persist(letter);

    given()
        .when().get("/letters")
        .then()
        .statusCode(200)
        .body("size()", is(1))
        .body("[0].cyrillic", is("a"))
        .body("[0].ipa", is("a"))
        .body("[0].latin", is("a"))
        .body("[0].createdAt", nullValue());
  }

}
