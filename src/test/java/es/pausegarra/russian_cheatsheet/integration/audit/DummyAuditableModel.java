package es.pausegarra.russian_cheatsheet.integration.audit;

import es.pausegarra.russian_cheatsheet.common.infrastructure.audit.AuditableModel;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.With;

import java.util.UUID;

@Entity
@Getter
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class DummyAuditableModel extends AuditableModel {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private final UUID id;

  @With
  private final String value;

  public static DummyAuditableModel create(String value) {
    return new DummyAuditableModel(null, value);
  }

}
