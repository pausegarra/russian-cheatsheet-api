package es.pausegarra.russian_cheatsheet.context.words.infrastructure.models;

import es.pausegarra.russian_cheatsheet.common.infrastructure.audit.AuditableModel;
import es.pausegarra.russian_cheatsheet.context.words.domain.entities.WordEntity;
import es.pausegarra.russian_cheatsheet.context.words.domain.enums.WordType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.With;
import lombok.experimental.SuperBuilder;

import java.util.Optional;
import java.util.UUID;

@Entity
@Table(name = "words")
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
@Getter
@SuperBuilder(toBuilder = true)
public class WordModel extends AuditableModel {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private final UUID id;

  private final String russian;

  private final String english;

  private final String spanish;

  @Enumerated(EnumType.STRING)
  private final WordType type;

  @OneToOne(mappedBy = "word", cascade = CascadeType.ALL)
  @With
  private final WordConjugationModel conjugations;

  @OneToOne(mappedBy = "word", cascade = CascadeType.ALL)
  @With
  private final WordDeclinationModel declinations;

  @OneToOne(mappedBy = "word", cascade = CascadeType.ALL)
  @With
  private final WordDeclinationMatrixModel declinationMatrix;

  public static WordModel fromEntity(WordEntity word) {
    WordModel wordModel = WordModel.builder()
      .id(word.id())
      .russian(word.russian())
      .english(word.english())
      .spanish(word.spanish())
      .type(word.type())
      .createdBy(word.createdBy())
      .createdAt(word.createdAt())
      .updatedBy(word.updatedBy())
      .updatedAt(word.updatedAt())
      .build();

    return wordModel.toBuilder()
      .conjugations(WordConjugationModel.fromEntity(word.conjugations(), wordModel))
      .declinations(WordDeclinationModel.fromEntity(word.declinations(), wordModel))
      .declinationMatrix(WordDeclinationMatrixModel.fromEntity(word.declinationMatrix(), wordModel))
      .build();
  }

  public WordEntity toEntity() {
    WordEntity wordEntity = WordEntity.builder()
      .id(id)
      .russian(russian)
      .english(english)
      .spanish(spanish)
      .type(type)
      .createdBy(createdBy)
      .createdAt(createdAt)
      .updatedBy(updatedBy)
      .updatedAt(updatedAt)
      .build();

    return wordEntity.withConjugations(Optional.ofNullable(conjugations).map((conjugations) -> conjugations.toEntity(wordEntity)).orElse(null))
      .withDeclinations(Optional.ofNullable(declinations).map((declinations) -> declinations.toEntity(wordEntity)).orElse(null))
      .withDeclinationMatrix(Optional.ofNullable(declinationMatrix).map((declinationMatrix) -> declinationMatrix.toEntity(wordEntity)).orElse(null));
  }

}
