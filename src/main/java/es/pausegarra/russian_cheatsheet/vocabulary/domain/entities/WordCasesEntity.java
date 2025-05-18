package es.pausegarra.russian_cheatsheet.vocabulary.domain.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "word_cases")
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
@Builder
@Getter
public class WordCasesEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private final UUID id;

  @OneToOne(optional = false)
  @JoinColumn(
    name = "word_id", nullable = false, unique = true
  )
  @With
  private final WordEntity word;

  @Column(name = "nominative_masculine")
  private final String nominativeMasculine;

  @Column(name = "nominative_feminine")
  private final String nominativeFeminine;

  @Column(name = "nominative_neuter")
  private final String nominativeNeuter;

  @Column(name = "genitive_masculine")
  private final String genitiveMasculine;

  @Column(name = "genitive_feminine")
  private final String genitiveFeminine;

  @Column(name = "genitive_neuter")
  private final String genitiveNeuter;

  @Column(name = "dative_masculine")
  private final String dativeMasculine;

  @Column(name = "dative_feminine")
  private final String dativeFeminine;

  @Column(name = "dative_neuter")
  private final String dativeNeuter;

  @Column(name = "accusative_masculine")
  private final String accusativeMasculine;

  @Column(name = "accusative_feminine")
  private final String accusativeFeminine;

  @Column(name = "accusative_neuter")
  private final String accusativeNeuter;

  @Column(name = "instrumental_masculine")
  private final String instrumentalMasculine;

  @Column(name = "instrumental_feminine")
  private final String instrumentalFeminine;

  @Column(name = "instrumental_neuter")
  private final String instrumentalNeuter;

  @Column(name = "prepositional_masculine")
  private final String prepositionalMasculine;

  @Column(name = "prepositional_feminine")
  private final String prepositionalFeminine;

  @Column(name = "prepositional_neuter")
  private final String prepositionalNeuter;

  public WordCasesEntity update(WordCasesEntity entity) {
    return new WordCasesEntity(
      getId(),
      getWord(),
      entity.getNominativeMasculine(),
      entity.getNominativeFeminine(),
      entity.getNominativeNeuter(),
      entity.getGenitiveMasculine(),
      entity.getGenitiveFeminine(),
      entity.getGenitiveNeuter(),
      entity.getDativeMasculine(),
      entity.getDativeFeminine(),
      entity.getDativeNeuter(),
      entity.getAccusativeMasculine(),
      entity.getAccusativeFeminine(),
      entity.getAccusativeNeuter(),
      entity.getInstrumentalMasculine(),
      entity.getInstrumentalFeminine(),
      entity.getInstrumentalNeuter(),
      entity.getPrepositionalMasculine(),
      entity.getPrepositionalFeminine(),
      entity.getPrepositionalNeuter()
    );
  }

}
