package es.pausegarra.russkiy_po_moyemu.annotations;

import com.fasterxml.jackson.databind.ObjectMapper;
import es.pausegarra.russkiy_po_moyemu.config.PostgreSqlTestContainer;
import io.quarkus.test.common.QuarkusTestResource;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@QuarkusTestResource(PostgreSqlTestContainer.class)
@Transactional
public abstract class IntegrationTest {

  protected final ObjectMapper objectMapper = new ObjectMapper();

  @PersistenceContext
  protected EntityManager em;

}
