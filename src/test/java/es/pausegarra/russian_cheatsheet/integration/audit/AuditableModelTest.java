package es.pausegarra.russian_cheatsheet.integration.audit;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
@Slf4j
class AuditableModelTest {

  @PersistenceContext
  protected EntityManager em;

  @Transactional
  protected <T> T merge(T object) {
    return em.merge(object);
  }

  @Transactional
  protected <T> T persist(T object) {
    em.persist(object);
    return object;
  }

  @Test
  @TestSecurity(user = "test")
  void shouldCreateAndSaveAuditableEntity() {
    DummyAuditableModel entity = DummyAuditableModel.create("value");

    persist(entity);

    DummyAuditableModel persisted = em.find(DummyAuditableModel.class, entity.getId());

    assertNotNull(persisted);
    assertNotNull(persisted.getCreatedAt());
    assertEquals("test", persisted.getCreatedBy());
    assertNull(persisted.getUpdatedAt());
    assertNull(persisted.getUpdatedBy());
  }

  @Test
  void shouldCreateAndSaveAuditableEntityEvenIfNoSecurityIdentityIsPresent() {
    DummyAuditableModel entity = DummyAuditableModel.create("value");

    persist(entity);

    DummyAuditableModel persisted = em.find(DummyAuditableModel.class, entity.getId());

    assertNotNull(persisted);
    assertNotNull(persisted.getCreatedAt());
    assertEquals("anonymous", persisted.getCreatedBy());
    assertNull(persisted.getUpdatedAt());
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
