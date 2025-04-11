package es.pausegarra.russkiy_po_moyemu.integration.vocabulary;

import es.pausegarra.russkiy_po_moyemu.annotations.IntegrationTest;
import es.pausegarra.russkiy_po_moyemu.vocabulary.domain.entities.WordEntity;
import es.pausegarra.russkiy_po_moyemu.vocabulary.domain.enums.WordTypes;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertNull;

@QuarkusTest
public class DeleteWordTest extends IntegrationTest {

  @Transactional
  public WordEntity createWord() {
    WordEntity word = WordEntity.create(
      null,
      "Russkiy po moyemu",
      "Russkiy po moyemu",
      "russkiy po moyemu",
      WordTypes.VERB
    );
    em.persist(word);

    return word;
  }

  @Test
  @TestSecurity(user = "user", roles = "words#delete")
  public void shouldDeleteWord() {
    WordEntity word = createWord();

    given()
      .when()
      .delete("/words/" + word.getId())
      .then()
      .statusCode(204);

    WordEntity deletedWord = em.find(WordEntity.class, word.getId());
    assertNull(deletedWord);
  }

  @Test
  @TestSecurity(user = "user", roles = "words#delete")
  public void shouldReturn404IfWordNotFound() {
    given()
      .when()
      .delete("/words/" + UUID.randomUUID())
      .then()
      .statusCode(404);
  }

  @Test
  public void shouldReturn401IfUserIsNotAuthenticated() {
    given()
      .when()
      .delete("/words/" + UUID.randomUUID())
      .then()
      .statusCode(401);
  }

  @Test
  @TestSecurity(user = "user")
  public void shouldReturn403IfUserHasNoRole() {
    given()
      .when()
      .delete("/words/" + UUID.randomUUID())
      .then()
      .statusCode(403);
  }

}
