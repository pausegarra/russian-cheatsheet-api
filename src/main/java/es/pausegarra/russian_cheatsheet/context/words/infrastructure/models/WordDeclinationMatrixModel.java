package es.pausegarra.russian_cheatsheet.context.words.infrastructure.models;

import es.pausegarra.russian_cheatsheet.common.infrastructure.audit.AuditableModel;
import es.pausegarra.russian_cheatsheet.context.words.domain.entities.WordDeclinationEntity;
import es.pausegarra.russian_cheatsheet.context.words.domain.entities.WordDeclinationMatrixEntity;
import es.pausegarra.russian_cheatsheet.context.words.domain.entities.WordEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Entity
@Table(name = "word_declination_matrix")
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
@Getter
@SuperBuilder
public class WordDeclinationMatrixModel extends AuditableModel {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private final UUID id;

  @Column(name = "nominative_masculine")
  private final String nominativeMasculine;

  @Column(name = "nominative_feminine")
  private final String nominativeFeminine;

  @Column(name = "nominative_neuter")
  private final String nominativeNeuter;

  @Column(name = "nominative_plural")
  private final String nominativePlural;

  @Column(name = "accusative_masculine")
  private final String accusativeMasculine;

  @Column(name = "accusative_feminine")
  private final String accusativeFeminine;

  @Column(name = "accusative_neuter")
  private final String accusativeNeuter;

  @Column(name = "accusative_plural")
  private final String accusativePlural;

  @Column(name = "genitive_masculine")
  private final String genitiveMasculine;

  @Column(name = "genitive_feminine")
  private final String genitiveFeminine;

  @Column(name = "genitive_neuter")
  private final String genitiveNeuter;

  @Column(name = "genitive_plural")
  private final String genitivePlural;

  @Column(name = "dative_masculine")
  private final String dativeMasculine;

  @Column(name = "dative_feminine")
  private final String dativeFeminine;

  @Column(name = "dative_neuter")
  private final String dativeNeuter;

  @Column(name = "dative_plural")
  private final String dativePlural;

  @Column(name = "instrumental_masculine")
  private final String instrumentalMasculine;

  @Column(name = "instrumental_feminine")
  private final String instrumentalFeminine;

  @Column(name = "instrumental_neuter")
  private final String instrumentalNeuter;

  @Column(name = "instrumental_plural")
  private final String instrumentalPlural;

  @Column(name = "prepositional_masculine")
  private final String prepositionalMasculine;

  @Column(name = "prepositional_feminine")
  private final String prepositionalFeminine;

  @Column(name = "prepositional_neuter")
  private final String prepositionalNeuter;

  @Column(name = "prepositional_plural")
  private final String prepositionalPlural;

  @OneToOne
  @JoinColumn(name = "word_id")
  private final WordModel word;

  public static WordDeclinationMatrixModel fromEntity(WordDeclinationMatrixEntity wordDeclinationMatrixEntity, WordModel word) {
    if (wordDeclinationMatrixEntity == null) return null;

    return WordDeclinationMatrixModel.builder()
      .id(wordDeclinationMatrixEntity.id())
      .nominativeMasculine(wordDeclinationMatrixEntity.nominativeMasculine())
      .nominativeFeminine(wordDeclinationMatrixEntity.nominativeFeminine())
      .nominativeNeuter(wordDeclinationMatrixEntity.nominativeNeuter())
      .nominativePlural(wordDeclinationMatrixEntity.nominativePlural())
      .accusativeMasculine(wordDeclinationMatrixEntity.accusativeMasculine())
      .accusativeFeminine(wordDeclinationMatrixEntity.accusativeFeminine())
      .accusativeNeuter(wordDeclinationMatrixEntity.accusativeNeuter())
      .accusativePlural(wordDeclinationMatrixEntity.accusativePlural())
      .genitiveMasculine(wordDeclinationMatrixEntity.genitiveMasculine())
      .genitiveFeminine(wordDeclinationMatrixEntity.genitiveFeminine())
      .genitiveNeuter(wordDeclinationMatrixEntity.genitiveNeuter())
      .genitivePlural(wordDeclinationMatrixEntity.genitivePlural())
      .dativeMasculine(wordDeclinationMatrixEntity.dativeMasculine())
      .dativeFeminine(wordDeclinationMatrixEntity.dativeFeminine())
      .dativeNeuter(wordDeclinationMatrixEntity.dativeNeuter())
      .dativePlural(wordDeclinationMatrixEntity.dativePlural())
      .instrumentalMasculine(wordDeclinationMatrixEntity.instrumentalMasculine())
      .instrumentalFeminine(wordDeclinationMatrixEntity.instrumentalFeminine())
      .instrumentalNeuter(wordDeclinationMatrixEntity.instrumentalNeuter())
      .instrumentalPlural(wordDeclinationMatrixEntity.instrumentalPlural())
      .prepositionalMasculine(wordDeclinationMatrixEntity.prepositionalMasculine())
      .prepositionalFeminine(wordDeclinationMatrixEntity.prepositionalFeminine())
      .prepositionalNeuter(wordDeclinationMatrixEntity.prepositionalNeuter())
      .prepositionalPlural(wordDeclinationMatrixEntity.prepositionalPlural())
      .word(word)
      .createdBy(wordDeclinationMatrixEntity.createdBy())
      .createdAt(wordDeclinationMatrixEntity.createdAt())
      .updatedBy(wordDeclinationMatrixEntity.updatedBy())
      .updatedAt(wordDeclinationMatrixEntity.updatedAt())
      .build();
  }

  public WordDeclinationMatrixEntity toEntity(WordEntity word) {
    return WordDeclinationMatrixEntity.builder()
      .id(id)
      .nominativeMasculine(nominativeMasculine)
      .nominativeFeminine(nominativeFeminine)
      .nominativeNeuter(nominativeNeuter)
      .nominativePlural(nominativePlural)
      .accusativeMasculine(accusativeMasculine)
      .accusativeFeminine(accusativeFeminine)
      .accusativeNeuter(accusativeNeuter)
      .accusativePlural(accusativePlural)
      .genitiveMasculine(genitiveMasculine)
      .genitiveFeminine(genitiveFeminine)
      .genitiveNeuter(genitiveNeuter)
      .genitivePlural(genitivePlural)
      .dativeMasculine(dativeMasculine)
      .dativeFeminine(dativeFeminine)
      .dativeNeuter(dativeNeuter)
      .dativePlural(dativePlural)
      .instrumentalMasculine(instrumentalMasculine)
      .instrumentalFeminine(instrumentalFeminine)
      .instrumentalNeuter(instrumentalNeuter)
      .instrumentalPlural(instrumentalPlural)
      .prepositionalMasculine(prepositionalMasculine)
      .prepositionalFeminine(prepositionalFeminine)
      .prepositionalNeuter(prepositionalNeuter)
      .prepositionalPlural(prepositionalPlural)
      .word(word)
      .createdBy(createdBy)
      .createdAt(createdAt)
      .updatedBy(updatedBy)
      .updatedAt(updatedAt)
      .build();
  }

}
