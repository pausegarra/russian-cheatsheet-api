package es.pausegarra.russian_cheatsheet.integration.vocabulary;

import com.fasterxml.jackson.core.JsonProcessingException;
import es.pausegarra.russian_cheatsheet.annotations.IntegrationTest;
import es.pausegarra.russian_cheatsheet.vocabulary.domain.entities.WordEntity;
import es.pausegarra.russian_cheatsheet.vocabulary.domain.enums.WordTypes;
import es.pausegarra.russian_cheatsheet.vocabulary.infrastructure.requests.UpdateWordRequest;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.smallrye.common.constraint.Assert.assertNotNull;
import static io.smallrye.common.constraint.Assert.assertTrue;
import static org.hamcrest.Matchers.*;

@QuarkusTest
public class UpdateWordTest extends IntegrationTest {

  @Transactional
  public WordEntity setUp() {
    WordEntity word = WordEntity.create(null, "word", "english", "spanish", null);
    em.persist(word);

    return word;
  }

  @Test
  @TestSecurity(
    user = "user",
    roles = "words#update"
  )
  public void shouldUpdateWord() throws JsonProcessingException {
    WordEntity word = setUp();

    UpdateWordRequest request = new UpdateWordRequest("word-r", "english-u", "spanish-u", "ADVERB");
    String json = objectMapper.writeValueAsString(request);
    given().body(json)
      .contentType("application/json")
      .when()
      .patch("/words/" + word.getId())
      .then()
      .statusCode(204);

    WordEntity updated = em.find(WordEntity.class, word.getId());
    assertNotNull(updated);
    assertTrue(updated.getRussian()
                 .equals("word-r"));
    assertTrue(updated.getEnglish()
                 .equals("english-u"));
    assertTrue(updated.getSpanish()
                 .equals("spanish-u"));
    assertTrue(updated.getType()
                 .equals(WordTypes.ADVERB));
  }

  @Test
  @TestSecurity(
    user = "user",
    roles = "words#update"
  )
  public void shouldReturn400WhenDataIsInvalid() throws JsonProcessingException {
    WordEntity word = setUp();

    UpdateWordRequest request = new UpdateWordRequest(null, null, null, "VERB");
    String json = objectMapper.writeValueAsString(request);
    given().body(json)
      .when()
      .contentType("application/json")
      .patch("/words/" + word.getId())
      .then()
      .statusCode(400)
      .body("errors.size()", is(3))
      .body("errors.field", hasItems("handle.dto.russian", "handle.dto.english", "handle.dto.spanish"))
      .body("errors.message", hasItem("must not be blank"));
  }

  @Test
  public void shouldReturn401WhenUserIsNotAuthenticated() throws JsonProcessingException {
    WordEntity word = setUp();

    UpdateWordRequest request = new UpdateWordRequest("word", "english", "spanish", "VERB");
    String json = objectMapper.writeValueAsString(request);
    given().body(json)
      .when()
      .patch("/words/" + word.getId())
      .then()
      .statusCode(401);
  }

  @Test
  @TestSecurity(user = "test")
  public void shouldReturn403WhenUserDoesNotHavePermissions() throws JsonProcessingException {
    WordEntity word = setUp();

    UpdateWordRequest request = new UpdateWordRequest("word", "english", "spanish", "VERB");
    String json = objectMapper.writeValueAsString(request);
    given().body(json)
      .when()
      .patch("/words/" + word.getId())
      .then()
      .statusCode(403);
  }

}
