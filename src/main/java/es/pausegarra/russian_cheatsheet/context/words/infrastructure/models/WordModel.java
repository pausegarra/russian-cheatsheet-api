package es.pausegarra.russian_cheatsheet.context.words.infrastructure.models;

import es.pausegarra.russian_cheatsheet.common.infrastructure.audit.AuditableModel;
import es.pausegarra.russian_cheatsheet.common.infrastructure.jsonb.WordConjugationJsonConverter;
import es.pausegarra.russian_cheatsheet.common.infrastructure.jsonb.WordDeclinationJsonConverter;
import es.pausegarra.russian_cheatsheet.common.infrastructure.jsonb.WordDeclinationMatrixJsonConverter;
import es.pausegarra.russian_cheatsheet.context.words.domain.entities.WordConjugationEntity;
import es.pausegarra.russian_cheatsheet.context.words.domain.entities.WordDeclinationEntity;
import es.pausegarra.russian_cheatsheet.context.words.domain.entities.WordDeclinationMatrixEntity;
import es.pausegarra.russian_cheatsheet.context.words.domain.entities.WordEntity;
import es.pausegarra.russian_cheatsheet.context.words.domain.enums.WordType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.FilterDefs;
import org.hibernate.annotations.Filters;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(
  name = "words", indexes = {@Index(name = "words_russian_english_spanish_idx", columnList = "russian, spanish, english")}
)
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
@Getter
@SuperBuilder(toBuilder = true)
@FilterDefs({
  @FilterDef(name = "publishedAt.notNull"),
  @FilterDef(name = "publishedAt.null")
})
@Filters({
  @Filter(name = "publishedAt.notNull", condition = "published_at is not null"),
  @Filter(name = "publishedAt.null", condition = "published_at is null")
})
public class WordModel extends AuditableModel {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private final UUID id;

  @Column(name = "russian", unique = true)
  private final String russian;

  private final String english;

  private final String spanish;

  @Enumerated(EnumType.STRING)
  private final WordType type;

  @Column(name = "conjugations", columnDefinition = "jsonb")
  @Convert(converter = WordConjugationJsonConverter.class)
  private final WordConjugationJson conjugations;

  @Column(name = "declinations", columnDefinition = "jsonb")
  @Convert(converter = WordDeclinationJsonConverter.class)
  private final WordDeclinationJson declinations;

  @Column(name = "declination_matrix", columnDefinition = "jsonb")
  @Convert(converter = WordDeclinationMatrixJsonConverter.class)
  private final WordDeclinationMatrixJson declinationMatrix;

  @Column(name = "published_at")
  private final Instant publishedAt;

  public static WordModel fromEntity(WordEntity word) {
    WordConjugationJson conjugationsJson = word.conjugations() != null ? toConjugationJson(word.conjugations()) : null;
    WordDeclinationJson declinationsJson = word.declinations() != null ? toDeclinationJson(word.declinations()) : null;
    WordDeclinationMatrixJson declinationMatrixJson = word.declinationMatrix() != null ? toDeclinationMatrixJson(word.declinationMatrix()) : null;

    return WordModel.builder()
      .id(word.id())
      .russian(word.russian())
      .english(word.english())
      .spanish(word.spanish())
      .type(word.type())
      .conjugations(conjugationsJson)
      .declinations(declinationsJson)
      .declinationMatrix(declinationMatrixJson)
      .publishedAt(word.publishedAt())
      .createdBy(word.createdBy())
      .createdAt(word.createdAt())
      .updatedBy(word.updatedBy())
      .updatedAt(word.updatedAt())
      .build();
  }

  public WordEntity toEntity() {
    WordConjugationEntity conjugationsEntity = conjugations != null ? fromConjugationJson(conjugations) : null;
    WordDeclinationEntity declinationsEntity = declinations != null ? fromDeclinationJson(declinations) : null;
    WordDeclinationMatrixEntity declinationMatrixEntity = declinationMatrix != null ? fromDeclinationMatrixJson(declinationMatrix) : null;

    return WordEntity.builder()
      .id(id)
      .russian(russian)
      .english(english)
      .spanish(spanish)
      .type(type)
      .conjugations(conjugationsEntity)
      .declinations(declinationsEntity)
      .declinationMatrix(declinationMatrixEntity)
      .publishedAt(publishedAt)
      .createdBy(createdBy)
      .createdAt(createdAt)
      .updatedBy(updatedBy)
      .updatedAt(updatedAt)
      .build();
  }

  private static WordConjugationJson toConjugationJson(WordConjugationEntity entity) {
    return new WordConjugationJson(
      entity.imperfectivePresentFirstPersonSingular(),
      entity.imperfectivePresentSecondPersonSingular(),
      entity.imperfectivePresentThirdPersonSingular(),
      entity.imperfectivePresentFirstPersonPlural(),
      entity.imperfectivePresentSecondPersonPlural(),
      entity.imperfectivePresentThirdPersonPlural(),
      entity.imperfectivePastMasculine(),
      entity.imperfectivePastFeminine(),
      entity.imperfectivePastNeuter(),
      entity.imperfectivePastPlural(),
      entity.imperfectiveFutureFirstPersonSingular(),
      entity.imperfectiveFutureSecondPersonSingular(),
      entity.imperfectiveFutureThirdPersonSingular(),
      entity.imperfectiveFutureFirstPersonPlural(),
      entity.imperfectiveFutureSecondPersonPlural(),
      entity.imperfectiveFutureThirdPersonPlural(),
      entity.perfectivePastMasculine(),
      entity.perfectivePastFeminine(),
      entity.perfectivePastNeuter(),
      entity.perfectivePastPlural(),
      entity.perfectiveFutureFirstPersonSingular(),
      entity.perfectiveFutureSecondPersonSingular(),
      entity.perfectiveFutureThirdPersonSingular(),
      entity.perfectiveFutureFirstPersonPlural(),
      entity.perfectiveFutureSecondPersonPlural(),
      entity.perfectiveFutureThirdPersonPlural()
    );
  }

  private static WordConjugationEntity fromConjugationJson(WordConjugationJson json) {
    return WordConjugationEntity.builder()
      .imperfectivePresentFirstPersonSingular(json.imperfectivePresentFirstPersonSingular())
      .imperfectivePresentSecondPersonSingular(json.imperfectivePresentSecondPersonSingular())
      .imperfectivePresentThirdPersonSingular(json.imperfectivePresentThirdPersonSingular())
      .imperfectivePresentFirstPersonPlural(json.imperfectivePresentFirstPersonPlural())
      .imperfectivePresentSecondPersonPlural(json.imperfectivePresentSecondPersonPlural())
      .imperfectivePresentThirdPersonPlural(json.imperfectivePresentThirdPersonPlural())
      .imperfectivePastMasculine(json.imperfectivePastMasculine())
      .imperfectivePastFeminine(json.imperfectivePastFeminine())
      .imperfectivePastNeuter(json.imperfectivePastNeuter())
      .imperfectivePastPlural(json.imperfectivePastPlural())
      .imperfectiveFutureFirstPersonSingular(json.imperfectiveFutureFirstPersonSingular())
      .imperfectiveFutureSecondPersonSingular(json.imperfectiveFutureSecondPersonSingular())
      .imperfectiveFutureThirdPersonSingular(json.imperfectiveFutureThirdPersonSingular())
      .imperfectiveFutureFirstPersonPlural(json.imperfectiveFutureFirstPersonPlural())
      .imperfectiveFutureSecondPersonPlural(json.imperfectiveFutureSecondPersonPlural())
      .imperfectiveFutureThirdPersonPlural(json.imperfectiveFutureThirdPersonPlural())
      .perfectivePastMasculine(json.perfectivePastMasculine())
      .perfectivePastFeminine(json.perfectivePastFeminine())
      .perfectivePastNeuter(json.perfectivePastNeuter())
      .perfectivePastPlural(json.perfectivePastPlural())
      .perfectiveFutureFirstPersonSingular(json.perfectiveFutureFirstPersonSingular())
      .perfectiveFutureSecondPersonSingular(json.perfectiveFutureSecondPersonSingular())
      .perfectiveFutureThirdPersonSingular(json.perfectiveFutureThirdPersonSingular())
      .perfectiveFutureFirstPersonPlural(json.perfectiveFutureFirstPersonPlural())
      .perfectiveFutureSecondPersonPlural(json.perfectiveFutureSecondPersonPlural())
      .perfectiveFutureThirdPersonPlural(json.perfectiveFutureThirdPersonPlural())
      .build();
  }

  private static WordDeclinationJson toDeclinationJson(WordDeclinationEntity entity) {
    return new WordDeclinationJson(
      entity.nominative(),
      entity.accusative(),
      entity.genitive(),
      entity.dative(),
      entity.instrumental(),
      entity.prepositional(),
      entity.nominativePlural(),
      entity.accusativePlural(),
      entity.genitivePlural(),
      entity.dativePlural(),
      entity.instrumentalPlural(),
      entity.prepositionalPlural()
    );
  }

  private static WordDeclinationEntity fromDeclinationJson(WordDeclinationJson json) {
    return WordDeclinationEntity.builder()
      .nominative(json.nominative())
      .accusative(json.accusative())
      .genitive(json.genitive())
      .dative(json.dative())
      .instrumental(json.instrumental())
      .prepositional(json.prepositional())
      .nominativePlural(json.nominativePlural())
      .accusativePlural(json.accusativePlural())
      .genitivePlural(json.genitivePlural())
      .dativePlural(json.dativePlural())
      .instrumentalPlural(json.instrumentalPlural())
      .prepositionalPlural(json.prepositionalPlural())
      .build();
  }

  private static WordDeclinationMatrixJson toDeclinationMatrixJson(WordDeclinationMatrixEntity entity) {
    return new WordDeclinationMatrixJson(
      entity.nominativeMasculine(),
      entity.nominativeFeminine(),
      entity.nominativeNeuter(),
      entity.nominativePlural(),
      entity.accusativeMasculine(),
      entity.accusativeFeminine(),
      entity.accusativeNeuter(),
      entity.accusativePlural(),
      entity.genitiveMasculine(),
      entity.genitiveFeminine(),
      entity.genitiveNeuter(),
      entity.genitivePlural(),
      entity.dativeMasculine(),
      entity.dativeFeminine(),
      entity.dativeNeuter(),
      entity.dativePlural(),
      entity.instrumentalMasculine(),
      entity.instrumentalFeminine(),
      entity.instrumentalNeuter(),
      entity.instrumentalPlural(),
      entity.prepositionalMasculine(),
      entity.prepositionalFeminine(),
      entity.prepositionalNeuter(),
      entity.prepositionalPlural()
    );
  }

  private static WordDeclinationMatrixEntity fromDeclinationMatrixJson(WordDeclinationMatrixJson json) {
    return WordDeclinationMatrixEntity.builder()
      .nominativeMasculine(json.nominativeMasculine())
      .nominativeFeminine(json.nominativeFeminine())
      .nominativeNeuter(json.nominativeNeuter())
      .nominativePlural(json.nominativePlural())
      .accusativeMasculine(json.accusativeMasculine())
      .accusativeFeminine(json.accusativeFeminine())
      .accusativeNeuter(json.accusativeNeuter())
      .accusativePlural(json.accusativePlural())
      .genitiveMasculine(json.genitiveMasculine())
      .genitiveFeminine(json.genitiveFeminine())
      .genitiveNeuter(json.genitiveNeuter())
      .genitivePlural(json.genitivePlural())
      .dativeMasculine(json.dativeMasculine())
      .dativeFeminine(json.dativeFeminine())
      .dativeNeuter(json.dativeNeuter())
      .dativePlural(json.dativePlural())
      .instrumentalMasculine(json.instrumentalMasculine())
      .instrumentalFeminine(json.instrumentalFeminine())
      .instrumentalNeuter(json.instrumentalNeuter())
      .instrumentalPlural(json.instrumentalPlural())
      .prepositionalMasculine(json.prepositionalMasculine())
      .prepositionalFeminine(json.prepositionalFeminine())
      .prepositionalNeuter(json.prepositionalNeuter())
      .prepositionalPlural(json.prepositionalPlural())
      .build();
  }

}
