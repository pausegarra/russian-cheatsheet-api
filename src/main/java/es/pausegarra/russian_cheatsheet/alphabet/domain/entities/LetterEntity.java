package es.pausegarra.russian_cheatsheet.alphabet.domain.entities;

import es.pausegarra.russian_cheatsheet.common.domain.audit.AuditFields;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "letters")
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
@Getter
public class LetterEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private final UUID id;

  @Column(
    unique = true,
    nullable = false
  )
  private final String cyrillic;

  @Column(
    nullable = false,
    name = "latin"
  )
  private final String latin;

  @Column(
    nullable = false
  )
  private final String ipa;

  @Embedded
  private final AuditFields auditFields;

  public static LetterEntity create(UUID id, String letter, String ipa, String latinLetter) {
    return new LetterEntity(id, letter, ipa, latinLetter, null);
  }

}
