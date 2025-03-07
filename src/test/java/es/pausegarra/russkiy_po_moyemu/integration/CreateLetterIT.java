package es.pausegarra.russkiy_po_moyemu.integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import es.pausegarra.russkiy_po_moyemu.alphabet.domain.entities.LetterEntity;
import es.pausegarra.russkiy_po_moyemu.config.PostgreSqlTestContainer;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
@QuarkusTestResource(PostgreSqlTestContainer.class)
@Transactional
@TestTransaction
public class CreateLetterIT {

  private final ObjectMapper objectMapper = new ObjectMapper();

  @PersistenceContext
  private EntityManager em;

  @Test
  public void shouldCreateLetter() throws JsonProcessingException {
    LetterEntity letter = LetterEntity.create(null, "a", "a");
    String json = objectMapper.writeValueAsString(letter);

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

}
