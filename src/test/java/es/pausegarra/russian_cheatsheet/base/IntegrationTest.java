package es.pausegarra.russian_cheatsheet.base;

import es.pausegarra.russian_cheatsheet.config.PostgreSqlTestContainer;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.test.common.QuarkusTestResource;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

@QuarkusTestResource(
  PostgreSqlTestContainer.class
)
public class IntegrationTest {

  protected final ObjectMapper objectMapper = new ObjectMapper();

  @PersistenceContext
  protected EntityManager em;

  @AfterEach
  @BeforeEach
  @Transactional
  public void cleanUp() {
  }

  @Transactional
  protected <T> T merge(T object) {
    return em.merge(object);
  }

  @Transactional
  protected <T> T persist(T object) {
    em.persist(object);
    return object;
  }

}
