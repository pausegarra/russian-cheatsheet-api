package es.pausegarra.russian_cheatsheet.vocabulary.domain.entities;

import es.pausegarra.russian_cheatsheet.common.domain.audit.AuditFields;
import es.pausegarra.russian_cheatsheet.vocabulary.domain.enums.WordTypes;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.With;

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

  @Column(
    unique = true,
    nullable = false
  )
  @With
  private final String russian;

  @Column(
    unique = true,
    nullable = false
  )
  @With
  private final String english;

  @Column(
    unique = true,
    nullable = false
  )
  @With
  private final String spanish;

  @Enumerated(EnumType.STRING)
  @With
  private final WordTypes type;

  @OneToOne(
    mappedBy = "word",
    cascade = CascadeType.ALL,
    orphanRemoval = true
  )
  @With
  private final VerbConjugationEntity conjugations;

  @Embedded
  private final AuditFields auditFields;

  public static WordEntity create(UUID id, String russian, String english, String spanish, WordTypes type) {
    return new WordEntity(id, russian, english, spanish, type, null, null);
  }

  public WordEntity update(String russian, String english, String spanish, WordTypes type) {
    return this.withEnglish(english)
      .withRussian(russian)
      .withSpanish(spanish)
      .withType(type);
  }

}
