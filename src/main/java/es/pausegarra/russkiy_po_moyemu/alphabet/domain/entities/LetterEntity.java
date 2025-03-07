package es.pausegarra.russkiy_po_moyemu.alphabet.domain.entities;

import es.pausegarra.russkiy_po_moyemu.common.domain.audit.AuditFields;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "letters")
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(force = true)
@Getter
public class LetterEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private final UUID id;

  @Column(unique = true)
  private final String letter;

  @Column(unique = true)
  private final String ipa;

  @Embedded
  private final AuditFields auditFields;

  public static LetterEntity create(UUID id, String letter, String ipa) {
    return new LetterEntity(id, letter, ipa, null);
  }

}
