package es.pausegarra.russian_cheatsheet.integration.alphabet;

import com.fasterxml.jackson.core.JsonProcessingException;
import es.pausegarra.russian_cheatsheet.alphabet.domain.entities.LetterEntity;
import es.pausegarra.russian_cheatsheet.alphabet.infrastructure.requests.CreateLetterRequest;
import es.pausegarra.russian_cheatsheet.annotations.IntegrationTest;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
public class CreateLetterTest extends IntegrationTest {

  @Test
  @TestSecurity(
    user = "test",
    roles = {"letters#create"}
  )
  public void shouldCreateLetter() throws JsonProcessingException {
    CreateLetterRequest request = new CreateLetterRequest("a", "a", "a");
    String json = objectMapper.writeValueAsString(request);

    given().body(json)
      .contentType("application/json")
      .when()
      .post("/letters")
      .then()
      .statusCode(201);

    LetterEntity savedLetter = em.createQuery(
        "SELECT l FROM LetterEntity l WHERE l.cyrillic = :cyrillic", LetterEntity.class)
      .setParameter("cyrillic", "a")
      .getSingleResult();

    assertEquals("a", savedLetter.getCyrillic());
    assertEquals("a", savedLetter.getLatin());
    assertEquals("a", savedLetter.getIpa());
  }

  @Test
  @TestSecurity(
    user = "test",
    roles = {"letters#create"}
  )
  public void shouldReturn400WhenDataIsInvalid() throws JsonProcessingException {
    CreateLetterRequest request = new CreateLetterRequest(null, null, null);
    String json = objectMapper.writeValueAsString(request);

    given().body(json)
      .contentType("application/json")
      .when()
      .post("/letters")
      .then()
      .statusCode(400)
      .body("errors.size()", is(3))
      .body("errors.field", hasItems("handle.dto.cyrillic", "handle.dto.ipa", "handle.dto.latin"))
      .body("errors.message", hasItem("must not be blank"));
  }

  @Test
  @TestSecurity(user = "test")
  public void shouldReturn403WhenUserDoesNotHavePermissions() throws JsonProcessingException {
    CreateLetterRequest request = new CreateLetterRequest("a", "a", "a");
    String json = objectMapper.writeValueAsString(request);

    given().body(json)
      .contentType("application/json")
      .when()
      .post("/letters")
      .then()
      .statusCode(403);
  }

  @Test
  public void shouldReturn401WhenUserIsNotAuthenticated() throws JsonProcessingException {
    CreateLetterRequest request = new CreateLetterRequest("a", "a", "a");
    String json = objectMapper.writeValueAsString(request);

    given().body(json)
      .contentType("application/json")
      .when()
      .post("/letters")
      .then()
      .statusCode(401);
  }

}
