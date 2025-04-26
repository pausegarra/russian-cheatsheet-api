package es.pausegarra.russian_cheatsheet.integration.vocabulary;

import es.pausegarra.russian_cheatsheet.annotations.IntegrationTest;
import es.pausegarra.russian_cheatsheet.vocabulary.domain.entities.WordEntity;
import es.pausegarra.russian_cheatsheet.vocabulary.domain.enums.WordTypes;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@QuarkusTest
public class ListPaginatedWordsTest extends IntegrationTest {

  @Transactional
  public void setUp() {
    WordEntity word = WordEntity.create(null, "a", "a", "a", WordTypes.VERB);
    em.persist(word);

    word = WordEntity.create(null, "b", "b", "b", WordTypes.VERB);
    em.persist(word);

    em.flush();
  }

  @Test
  public void shouldReturnAllWords() {
    setUp();

    given().when()
      .get("/words")
      .then()
      .statusCode(200)
      .body("data.size()", is(2))
      .body("data[0].russian", is("a"))
      .body("data[0].english", is("a"))
      .body("data[0].spanish", is("a"))
      .body("data[0].type", is("VERB"))
      .body("data[0].createdAt", nullValue())
      .body("data[0].updatedAt", nullValue())
      .body("data[1].russian", is("b"))
      .body("data[1].english", is("b"))
      .body("data[1].spanish", is("b"))
      .body("data[1].type", is("VERB"))
      .body("data[1].createdAt", nullValue())
      .body("data[1].updatedAt", nullValue())
      .body("page", is(0))
      .body("pageSize", is(10))
      .body("totalPages", is(1))
      .body("totalElements", is(2))
      .body("hasNextPage", is(false))
      .body("hasPreviousPage", is(false));
  }

  @Test
  public void shouldChangePageAndPageSize() {
    given().queryParam("page", "1")
      .queryParam("pageSize", "5")
      .when()
      .get("/words")
      .then()
      .statusCode(200)
      .body("page", is(1))
      .body("pageSize", is(5));
  }

  @Test
  public void shouldSearch() {
    setUp();

    given().queryParam("search", "a")
      .when()
      .get("/words")
      .then()
      .statusCode(200)
      .body("data.size()", is(1))
      .body("data[0]", anyOf(
        hasEntry(equalTo("russian"), containsString("a")),
        hasEntry(equalTo("english"), containsString("a")),
        hasEntry(equalTo("spanish"), containsString("a"))
      ));
  }

}
