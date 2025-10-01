package es.pausegarra.template.integration.audit;

import es.pausegarra.template.base.IntegrationTest;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
@Slf4j
class AuditableModelTest extends IntegrationTest {

  @Test
  @TestSecurity(
    user = "test"
  )
  public void shouldCreateAndSaveAuditableEntity() {
  }

  @Test
  public void shouldCreateAndSaveAuditableEntityEvenIfNoSecurityIdentityIsPresent() {
  }

  @Test
  @TestSecurity(
    user = "test"
  )
  public void shouldSaveAuditableEntityThatHasBeenUpdatedWithUpdatedBy() {
  }

  @Test
  public void shouldSaveAuditableEntityThatHasBeenUpdatedWithUpdateByAnonymous() {
  }

}
