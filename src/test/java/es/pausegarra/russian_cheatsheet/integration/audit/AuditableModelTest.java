package es.pausegarra.russian_cheatsheet.integration.audit;

import es.pausegarra.russian_cheatsheet.base.IntegrationTest;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
@Slf4j
class AuditableModelTest extends IntegrationTest {

  @Test
  @TestSecurity(user = "test")
  void shouldCreateAndSaveAuditableEntity() {
    DummyAuditableModel entity = DummyAuditableModel.create("value");

    persist(entity);

    DummyAuditableModel persisted = em.find(DummyAuditableModel.class, entity.getId());

    assertNotNull(persisted);
    assertNotNull(persisted.getCreatedAt());
    assertNotNull(persisted.getUpdatedAt());
    assertEquals("test", persisted.getCreatedBy());
    assertNull(persisted.getUpdatedBy());
  }

  @Test
  void shouldCreateAndSaveAuditableEntityEvenIfNoSecurityIdentityIsPresent() {
    DummyAuditableModel entity = DummyAuditableModel.create("value");

    persist(entity);

    DummyAuditableModel persisted = em.find(DummyAuditableModel.class, entity.getId());

    assertNotNull(persisted);
    assertNotNull(persisted.getCreatedAt());
    assertNotNull(persisted.getUpdatedAt());
    assertEquals("anonymous", persisted.getCreatedBy());
    assertNull(persisted.getUpdatedBy());
  }

  @Test
  @TestSecurity(user = "test")
  void shouldSaveAuditableEntityThatHasBeenUpdatedWithUpdatedBy() {
    DummyAuditableModel entity = DummyAuditableModel.create("value");
    DummyAuditableModel created = persist(entity);

    merge(created.withValue("updated"));

    DummyAuditableModel updated = em.find(DummyAuditableModel.class, created.getId());

    assertNotNull(updated);
    assertEquals("updated", updated.getValue());
    assertEquals("test", updated.getUpdatedBy());
    assertNotNull(updated.getUpdatedAt());
  }

  @Test
  void shouldSaveAuditableEntityThatHasBeenUpdatedWithUpdateByAnonymous() {
    DummyAuditableModel entity = DummyAuditableModel.create("value");
    DummyAuditableModel created = persist(entity);

    merge(created.withValue("updated"));

    DummyAuditableModel updated = em.find(DummyAuditableModel.class, entity.getId());

    assertNotNull(updated);
    assertEquals("updated", updated.getValue());
    assertEquals("anonymous", updated.getUpdatedBy());
    assertNotNull(updated.getUpdatedAt());
  }
  
}
