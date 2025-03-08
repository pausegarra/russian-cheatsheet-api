package es.pausegarra.russkiy_po_moyemu.integration.alphabet;

import com.fasterxml.jackson.core.JsonProcessingException;
import es.pausegarra.russkiy_po_moyemu.alphabet.domain.entities.LetterEntity;
import es.pausegarra.russkiy_po_moyemu.alphabet.infrastructure.requests.CreateLetterRequest;
import es.pausegarra.russkiy_po_moyemu.annotations.IntegrationTest;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
public class CreateLetterTest extends IntegrationTest {

  @Test
  public void shouldCreateLetter() throws JsonProcessingException {
    CreateLetterRequest request = new CreateLetterRequest("a", "a");
    String json = objectMapper.writeValueAsString(request);

    given()
        .body(json)
        .contentType("application/json")
        .when().post("/letters")
        .then()
        .statusCode(201);

    LetterEntity savedLetter = em.createQuery(
        "SELECT l FROM LetterEntity l WHERE l.letter = :letter", LetterEntity.class)
        .setParameter("letter", "a")
        .getSingleResult();

    assertEquals("a", savedLetter.getLetter());
    assertEquals("a", savedLetter.getIpa());
  }

  @Test
  public void shouldReturn400WhenDataIsInvalid() throws JsonProcessingException {
    CreateLetterRequest request = new CreateLetterRequest(null, null);
    String json = objectMapper.writeValueAsString(request);

    given()
        .body(json)
        .contentType("application/json")
        .when().post("/letters")
        .then()
        .statusCode(400)
        .body("errors.size()", is(2))
        .body("errors[1].field", is("handle.command.letter"))
        .body("errors[1].message", is("must not be blank"))
        .body("errors[0].field", is("handle.command.ipa"))
        .body("errors[0].message", is("must not be blank"));
  }

}
