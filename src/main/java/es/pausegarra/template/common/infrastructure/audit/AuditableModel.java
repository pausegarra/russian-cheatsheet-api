package es.pausegarra.template.common.infrastructure.audit;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@MappedSuperclass
@Getter
@SuperBuilder(
  toBuilder = true
)
@AllArgsConstructor
@NoArgsConstructor
public abstract class AuditableModel {

  @CreationTimestamp
  @Column(
    name = "created_at"
  )
  protected Instant createdAt;

  @UpdateTimestamp
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
  }

  @PreUpdate
  public void preUpdate() {
    this.updatedBy = AuditorProvider.getCurrentUser();
  }

}
