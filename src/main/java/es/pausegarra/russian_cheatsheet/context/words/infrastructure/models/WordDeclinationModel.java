package es.pausegarra.russian_cheatsheet.context.words.infrastructure.models;

import es.pausegarra.russian_cheatsheet.common.infrastructure.audit.AuditableModel;
import es.pausegarra.russian_cheatsheet.context.words.domain.entities.WordDeclinationEntity;
import es.pausegarra.russian_cheatsheet.context.words.domain.entities.WordEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Entity
@Table(name = "word_declinations")
@Getter
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
@SuperBuilder
public class WordDeclinationModel extends AuditableModel {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private final UUID id;

  private final String nominative;

  private final String accusative;

  private final String genitive;

  private final String dative;

  private final String instrumental;

  private final String prepositional;

  @OneToOne
  @JoinColumn(name = "word_id")
  private final WordModel word;

  public static WordDeclinationModel fromEntity(WordDeclinationEntity wordDeclinationEntity, WordModel word) {
    if (wordDeclinationEntity == null) return null;

    return WordDeclinationModel.builder()
      .id(wordDeclinationEntity.id())
      .nominative(wordDeclinationEntity.nominative())
      .accusative(wordDeclinationEntity.accusative())
      .genitive(wordDeclinationEntity.genitive())
      .dative(wordDeclinationEntity.dative())
      .instrumental(wordDeclinationEntity.instrumental())
      .prepositional(wordDeclinationEntity.prepositional())
      .word(word)
      .createdBy(wordDeclinationEntity.createdBy())
      .createdAt(wordDeclinationEntity.createdAt())
      .updatedBy(wordDeclinationEntity.updatedBy())
      .updatedAt(wordDeclinationEntity.updatedAt())
      .build();
  }

  public WordDeclinationEntity toEntity(WordEntity word) {
    return WordDeclinationEntity.builder()
      .id(id)
      .nominative(nominative)
      .accusative(accusative)
      .genitive(genitive)
      .dative(dative)
      .instrumental(instrumental)
      .prepositional(prepositional)
      .word(word)
      .createdBy(createdBy)
      .createdAt(createdAt)
      .updatedBy(updatedBy)
      .updatedAt(updatedAt)
      .build();
  }

}
