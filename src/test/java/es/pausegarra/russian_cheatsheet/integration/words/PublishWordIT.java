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
import static org.junit.jupiter.api.Assertions.assertNotNull;

@QuarkusTest
public class PublishWordIT extends IntegrationTest {

  @Test
  @TestSecurity(user = "user", roles = "words#publish")
  public void shouldPublishWord() {
    WordEntity word = WordMother.random().id(null).publishedAt(null).build();
    WordModel saved = persist(WordModel.fromEntity(word));

    given().when().patch("/words/" + saved.getId().toString() + "/publish").then().statusCode(204);

    WordModel updated = em.find(WordModel.class, saved.getId());
    assertNotNull(updated);
    assertNotNull(updated.getPublishedAt());
  }

  @Test
  @TestSecurity(user = "user", roles = "words#publish")
  public void shouldReturn404IfWordNotFound() {
    given().when().patch("/words/" + UUID.randomUUID().toString() + "/publish").then().statusCode(404);
  }

  @Test
  public void shouldReturn401IfUserIsNotAuthenticated() {
    given().when().patch("/words/" + UUID.randomUUID().toString() + "/publish").then().statusCode(401);
  }

  @Test
  @TestSecurity(user = "user")
  public void shouldReturn403IfUserIsNotAuthorized() {
    given().when().patch("/words/" + UUID.randomUUID().toString() + "/publish").then().statusCode(403);
  }

}
