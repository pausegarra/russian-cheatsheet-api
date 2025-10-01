package es.pausegarra.template.base;

import es.pausegarra.template.config.PostgreSqlTestContainer;
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
    em.createNativeQuery("TRUNCATE TABLE users CASCADE").executeUpdate();
    em.createNativeQuery("TRUNCATE TABLE solutions CASCADE").executeUpdate();
    em.createNativeQuery("TRUNCATE TABLE projects CASCADE").executeUpdate();
    em.createNativeQuery("TRUNCATE TABLE authz_models CASCADE").executeUpdate();
    em.createNativeQuery("TRUNCATE TABLE type_definitions CASCADE").executeUpdate();
    em.createNativeQuery("TRUNCATE TABLE type_definitions_items CASCADE").executeUpdate();
    em.createNativeQuery("TRUNCATE TABLE tuples CASCADE").executeUpdate();
    em.createNativeQuery("TRUNCATE TABLE type_definitions_relations CASCADE").executeUpdate();
    em.createNativeQuery("TRUNCATE TABLE stores CASCADE").executeUpdate();
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
