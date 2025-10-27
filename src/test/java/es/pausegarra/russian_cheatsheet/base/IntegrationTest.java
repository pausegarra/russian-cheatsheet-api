package es.pausegarra.russian_cheatsheet.base;

import com.fasterxml.jackson.databind.ObjectMapper;
import es.pausegarra.russian_cheatsheet.config.PostgreSqlTestContainer;
import io.quarkus.test.common.QuarkusTestResource;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.AfterEach;

@QuarkusTestResource(
  PostgreSqlTestContainer.class
)
public class IntegrationTest {

  protected final ObjectMapper objectMapper = new ObjectMapper();

  @PersistenceContext
  protected EntityManager em;

  @Inject
  Flyway flyway;

  @AfterEach
  public void cleanUp() {
    flyway.clean();
    flyway.migrate();
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
