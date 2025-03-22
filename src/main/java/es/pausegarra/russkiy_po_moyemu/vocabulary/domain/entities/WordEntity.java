package es.pausegarra.russkiy_po_moyemu.vocabulary.domain.entities;

import es.pausegarra.russkiy_po_moyemu.common.domain.audit.AuditFields;
import es.pausegarra.russkiy_po_moyemu.vocabulary.domain.enums.WordTypes;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "words")
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
@Getter
public class WordEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private final UUID id;

  @Column(unique = true, nullable = false)
  private final String russian;

  @Column(unique = true, nullable = false)
  private final String english;

  @Column(unique = true, nullable = false)
  private final String spanish;

  @Enumerated(EnumType.STRING)
  private final WordTypes type;

  @Embedded
  private final AuditFields auditFields;

  public static WordEntity create(UUID id, String russian, String english, String spanish, WordTypes type) {
    return new WordEntity(id, russian, english, spanish, type, null);
  }

}
