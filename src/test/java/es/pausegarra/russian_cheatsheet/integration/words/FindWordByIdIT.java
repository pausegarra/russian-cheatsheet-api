package es.pausegarra.russian_cheatsheet.integration.words;

import es.pausegarra.russian_cheatsheet.base.IntegrationTest;
import es.pausegarra.russian_cheatsheet.context.words.domain.entities.WordEntity;
import es.pausegarra.russian_cheatsheet.context.words.infrastructure.models.WordModel;
import es.pausegarra.russian_cheatsheet.mother.WordMother;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

@QuarkusTest
public class FindWordByIdIT extends IntegrationTest {

  @Test
  public void shouldFindWordById() {
    WordEntity word = WordMother.random().id(null).build();
    WordModel saved = persist(WordModel.fromEntity(word));

    given().when().get("/words/" + saved.getId().toString()).then().statusCode(200).body("id", is(saved.getId().toString()));
  }

  @Test
  public void shouldReturn404IfWordNotFound() {
    given().when().get("/words/" + UUID.randomUUID().toString()).then().statusCode(404);
  }

}
