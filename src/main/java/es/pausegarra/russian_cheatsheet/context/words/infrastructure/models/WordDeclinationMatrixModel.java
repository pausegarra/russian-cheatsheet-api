package es.pausegarra.russian_cheatsheet.context.words.infrastructure.models;

import es.pausegarra.russian_cheatsheet.common.infrastructure.audit.AuditableModel;
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
}
