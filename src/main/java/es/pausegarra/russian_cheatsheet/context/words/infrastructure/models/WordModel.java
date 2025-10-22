package es.pausegarra.russian_cheatsheet.context.words.infrastructure.models;

import es.pausegarra.russian_cheatsheet.common.infrastructure.audit.AuditableModel;
import es.pausegarra.russian_cheatsheet.context.words.domain.enums.WordType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Entity
@Table(name = "words")
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
@Getter
@SuperBuilder
public class WordModel extends AuditableModel {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private final UUID id;

  private final String russian;

  private final String english;

  private final String spanish;

  @Enumerated(EnumType.STRING)
  private final WordType type;

  @OneToOne(mappedBy = "word")
  private final WordConjugationModel conjugations;

  @OneToOne(mappedBy = "word")
  private final WordDeclinationModel declinations;

  @OneToOne(mappedBy = "word")
  private final WordDeclinationMatrixModel declinationMatrix;

}
