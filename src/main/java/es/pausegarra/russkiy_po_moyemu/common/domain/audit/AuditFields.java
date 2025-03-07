package es.pausegarra.russkiy_po_moyemu.common.domain.audit;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Embeddable
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
public class AuditFields {

  @CreationTimestamp
  @Column(name = "created_at")
  private final String createdAt;

  @UpdateTimestamp
  @Column(name = "updated_at")
  private final String updatedAt;

}
