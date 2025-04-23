package es.pausegarra.russian_cheatsheet.integration.alphabet;

import es.pausegarra.russian_cheatsheet.alphabet.domain.entities.LetterEntity;
import es.pausegarra.russian_cheatsheet.annotations.IntegrationTest;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

@QuarkusTest
public class ListLetterTest extends IntegrationTest {

  @Transactional
  public void setUp() {
    LetterEntity letter = LetterEntity.create(null, "a", "a", "a");
    em.persist(letter);
    em.flush();
  }

  @Test
  public void shouldReturnAllLetters() {
    setUp();

    given().when()
      .get("/letters")
      .then()
      .statusCode(200)
      .body("size()", is(1))
      .body("[0].cyrillic", is("a"))
      .body("[0].ipa", is("a"))
      .body("[0].latin", is("a"))
      .body("[0].createdAt", nullValue())
      .body("[0].updatedAt", nullValue());
  }

}
