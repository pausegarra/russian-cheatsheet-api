package es.pausegarra.russkiy_po_moyemu.integration.vocabulary;

import com.fasterxml.jackson.core.JsonProcessingException;
import es.pausegarra.russkiy_po_moyemu.annotations.IntegrationTest;
import es.pausegarra.russkiy_po_moyemu.vocabulary.infrastructure.requests.CreateWordRequest;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@QuarkusTest
public class CreateWordTest extends IntegrationTest {

  @Test
  @TestSecurity(
    user = "test",
    roles = {"words#create"}
  )
  public void shouldCreateWord() throws JsonProcessingException {
    CreateWordRequest request = new CreateWordRequest("a", "a", "a", "VERB");
    String json = objectMapper.writeValueAsString(request);

    given().body(json)
      .contentType("application/json")
      .when()
      .post("/words")
      .then()
      .statusCode(201)
      .body(notNullValue());
  }

  @Test
  @TestSecurity(
    user = "test",
    roles = {"words#create"}
  )
  public void shouldReturn400WhenDataIsInvalid() throws JsonProcessingException {
    CreateWordRequest request = new CreateWordRequest(null, null, null, "VERB");
    String json = objectMapper.writeValueAsString(request);

    given().body(json)
      .contentType("application/json")
      .when()
      .post("/words")
      .then()
      .statusCode(400)
      .body("errors.size()", is(3))
      .body("errors.field", hasItems("handle.dto.russian", "handle.dto.english", "handle.dto.spanish"))
      .body("errors.message", hasItem("must not be blank"));
  }

  @Test
  public void shouldReturn401WhenUserIsNotAuthenticated() throws JsonProcessingException {
    CreateWordRequest request = new CreateWordRequest("a", "a", "a", "VERB");
    String json = objectMapper.writeValueAsString(request);

    given().body(json)
      .contentType("application/json")
      .when()
      .post("/words")
      .then()
      .statusCode(401);
  }

  @Test
  @TestSecurity(user = "test")
  public void shouldReturn403WhenUserDoesNotHavePermissions() throws JsonProcessingException {
    CreateWordRequest request = new CreateWordRequest("a", "a", "a", "VERB");
    String json = objectMapper.writeValueAsString(request);

    given().body(json)
      .contentType("application/json")
      .when()
      .post("/words")
      .then()
      .statusCode(403);
  }

}
