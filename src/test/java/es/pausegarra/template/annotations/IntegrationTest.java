package es.pausegarra.template.annotations;

import com.fasterxml.jackson.databind.ObjectMapper;
import es.pausegarra.template.config.PostgreSqlTestContainer;
import io.quarkus.test.common.QuarkusTestResource;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

@QuarkusTestResource(PostgreSqlTestContainer.class)
public abstract class IntegrationTest {

  protected final ObjectMapper objectMapper = new ObjectMapper();

  @PersistenceContext
  protected EntityManager em;

  @AfterEach
  @BeforeEach
  @Transactional
  public void cleanUp() {
    em.createNativeQuery("TRUNCATE TABLE letters CASCADE")
      .executeUpdate();
    em.createNativeQuery("TRUNCATE TABLE words CASCADE")
      .executeUpdate();
    em.createNativeQuery("TRUNCATE TABLE verb_conjugations CASCADE")
      .executeUpdate();
  }

}
