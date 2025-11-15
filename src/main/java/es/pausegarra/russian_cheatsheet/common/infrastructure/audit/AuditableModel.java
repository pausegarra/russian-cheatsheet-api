package es.pausegarra.russian_cheatsheet.common.infrastructure.audit;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.Instant;

@MappedSuperclass
@Getter
@SuperBuilder(
  toBuilder = true
)
@AllArgsConstructor
@NoArgsConstructor
public abstract class AuditableModel {

  @Column(
    name = "created_at"
  )
  protected Instant createdAt;

  @Column(
    name = "updated_at"
  )
  protected Instant updatedAt;

  @Column(
    name = "created_by"
  )
  protected String createdBy;

  @Column(
    name = "updated_by"
  )
  protected String updatedBy;

  @PrePersist
  public void prePersist() {
    this.createdBy = AuditorProvider.getCurrentUser();
    this.createdAt = Instant.now();
  }

  @PreUpdate
  public void preUpdate() {
    this.updatedBy = AuditorProvider.getCurrentUser();
    this.updatedAt = Instant.now();
  }

}
