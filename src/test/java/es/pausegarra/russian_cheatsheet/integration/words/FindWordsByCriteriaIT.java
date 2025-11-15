package es.pausegarra.russian_cheatsheet.integration.words;

import es.pausegarra.russian_cheatsheet.base.IntegrationTest;
import es.pausegarra.russian_cheatsheet.context.words.domain.entities.WordEntity;
import es.pausegarra.russian_cheatsheet.context.words.infrastructure.models.WordModel;
import es.pausegarra.russian_cheatsheet.mother.WordMother;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

@QuarkusTest
public class FindWordsByCriteriaIT extends IntegrationTest {

  @Test
  public void shouldFindWordsByCriteriaWithDefaultValues() {
    WordEntity word = WordMother.random().id(null).build();
    WordModel saved = persist(WordModel.fromEntity(word));

    given().when().get("/words").then().statusCode(200).body("data.size()", is(1)).body("data[0].id", is(saved.getId().toString()));
  }

  @Test
  public void shouldChangePaginationParameters() {
    List<WordEntity> words = List.of(WordMother.random().id(null).build(), WordMother.random().id(null).build());
    persist(WordModel.fromEntity(words.getFirst()));
    persist(WordModel.fromEntity(words.getLast()));

    given().when()
      .get("/words?page=1&perPage=1")
      .then()
      .statusCode(200)
      .body("data.size()", is(1))
      .body("page", is(1))
      .body("pageSize", is(1))
      .body("totalPages", is(2))
      .body("totalElements", is(2))
      .body("hasNextPage", is(false))
      .body("hasPreviousPage", is(true));
  }

  @Test
  public void shouldSearch() {
    List<WordEntity> words = List.of(
      WordMother.random().id(null).russian("search").build(),
      WordMother.random().id(null).russian("hidden").build()
    );
    persist(WordModel.fromEntity(words.getFirst()));
    persist(WordModel.fromEntity(words.getLast()));

    given().when().get("/words?search=search").then().statusCode(200).body("data.size()", is(1)).body("data[0].russian", is("search"));
  }

}
