package es.pausegarra.russian_cheatsheet.integration.words;

import es.pausegarra.russian_cheatsheet.base.IntegrationTest;
import es.pausegarra.russian_cheatsheet.context.words.domain.entities.WordEntity;
import es.pausegarra.russian_cheatsheet.context.words.infrastructure.models.WordModel;
import es.pausegarra.russian_cheatsheet.mother.WordMother;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertNull;

@QuarkusTest
public class DeleteWordIT extends IntegrationTest {

  @Test
  @TestSecurity(user = "user", roles = "words#delete")
  public void shouldDeleteWord() {
    WordEntity word = WordMother.random().build();
    WordModel saved = persist(WordModel.fromEntity(word));

    given().when().delete("/words/" + saved.getId()).then().statusCode(204);

    WordModel deleted = em.find(WordModel.class, saved.getId());
    assertNull(deleted);
  }

  @Test
  @TestSecurity(user = "user", roles = "words#delete")
  public void shouldReturn404IfWordNotFound() {
    given().when().delete("/words/" + UUID.randomUUID()).then().statusCode(404);
  }

  @Test
  public void shouldReturn401IfUserIsNotAuthenticated() {
    given().when().delete("/words/" + UUID.randomUUID()).then().statusCode(401);
  }

  @Test
  @TestSecurity(user = "user")
  public void shouldReturn403IfUserIsNotAuthorized() {
    given().when().delete("/words/" + UUID.randomUUID()).then().statusCode(403);
  }

}
