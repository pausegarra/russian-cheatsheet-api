package es.pausegarra.russian_cheatsheet.context.words.domain.entities;

import lombok.Builder;

import java.time.Instant;
import java.util.UUID;

@Builder
public record WordConjugationEntity(
  UUID id,
  WordEntity word,
  String imperfectivePresentFirstPersonSingular,
  String imperfectivePresentSecondPersonSingular,
  String imperfectivePresentThirdPersonSingular,
  String imperfectivePresentFirstPersonPlural,
  String imperfectivePresentSecondPersonPlural,
  String imperfectivePresentThirdPersonPlural,
  String imperfectivePastMasculine,
  String imperfectivePastFeminine,
  String imperfectivePastNeuter,
  String imperfectivePastPlural,
  String imperfectiveFutureFirstPersonSingular,
  String imperfectiveFutureSecondPersonSingular,
  String imperfectiveFutureThirdPersonSingular,
  String imperfectiveFutureFirstPersonPlural,
  String imperfectiveFutureSecondPersonPlural,
  String imperfectiveFutureThirdPersonPlural,
  String perfectivePastMasculine,
  String perfectivePastFeminine,
  String perfectivePastNeuter,
  String perfectivePastPlural,
  String perfectiveFutureFirstPersonSingular,
  String perfectiveFutureSecondPersonSingular,
  String perfectiveFutureThirdPersonSingular,
  String perfectiveFutureFirstPersonPlural,
  String perfectiveFutureSecondPersonPlural,
  String perfectiveFutureThirdPersonPlural,
  String createdBy,
  Instant createdAt,
  String updatedBy,
  Instant updatedAt
) {

  public static WordConjugationEntity create(
    String imperfectivePresentFirstPersonSingular,
    String imperfectivePresentSecondPersonSingular,
    String imperfectivePresentThirdPersonSingular,
    String imperfectivePresentFirstPersonPlural,
    String imperfectivePresentSecondPersonPlural,
    String imperfectivePresentThirdPersonPlural,
    String imperfectivePastMasculine,
    String imperfectivePastFeminine,
    String imperfectivePastNeuter,
    String imperfectivePastPlural,
    String imperfectiveFutureFirstPersonSingular,
    String imperfectiveFutureSecondPersonSingular,
    String imperfectiveFutureThirdPersonSingular,
    String imperfectiveFutureFirstPersonPlural,
    String imperfectiveFutureSecondPersonPlural,
    String imperfectiveFutureThirdPersonPlural,
    String perfectivePastMasculine,
    String perfectivePastFeminine,
    String perfectivePastNeuter,
    String perfectivePastPlural,
    String perfectiveFutureFirstPersonSingular,
    String perfectiveFutureSecondPersonSingular,
    String perfectiveFutureThirdPersonSingular,
    String perfectiveFutureFirstPersonPlural,
    String perfectiveFutureSecondPersonPlural,
    String perfectiveFutureThirdPersonPlural
  ) {
    return WordConjugationEntity.builder()
      .imperfectivePresentFirstPersonSingular(imperfectivePresentFirstPersonSingular)
      .imperfectivePresentSecondPersonSingular(imperfectivePresentSecondPersonSingular)
      .imperfectivePresentThirdPersonSingular(imperfectivePresentThirdPersonSingular)
      .imperfectivePresentFirstPersonPlural(imperfectivePresentFirstPersonPlural)
      .imperfectivePresentSecondPersonPlural(imperfectivePresentSecondPersonPlural)
      .imperfectivePresentThirdPersonPlural(imperfectivePresentThirdPersonPlural)
      .imperfectivePastMasculine(imperfectivePastMasculine)
      .imperfectivePastFeminine(imperfectivePastFeminine)
      .imperfectivePastNeuter(imperfectivePastNeuter)
      .imperfectivePastPlural(imperfectivePastPlural)
      .imperfectiveFutureFirstPersonSingular(imperfectiveFutureFirstPersonSingular)
      .imperfectiveFutureSecondPersonSingular(imperfectiveFutureSecondPersonSingular)
      .imperfectiveFutureThirdPersonSingular(imperfectiveFutureThirdPersonSingular)
      .imperfectiveFutureFirstPersonPlural(imperfectiveFutureFirstPersonPlural)
      .imperfectiveFutureSecondPersonPlural(imperfectiveFutureSecondPersonPlural)
      .imperfectiveFutureThirdPersonPlural(imperfectiveFutureThirdPersonPlural)
      .perfectivePastMasculine(perfectivePastMasculine)
      .perfectivePastFeminine(perfectivePastFeminine)
      .perfectivePastNeuter(perfectivePastNeuter)
      .perfectivePastPlural(perfectivePastPlural)
      .perfectiveFutureFirstPersonSingular(perfectiveFutureFirstPersonSingular)
      .perfectiveFutureSecondPersonSingular(perfectiveFutureSecondPersonSingular)
      .perfectiveFutureThirdPersonSingular(perfectiveFutureThirdPersonSingular)
      .perfectiveFutureFirstPersonPlural(perfectiveFutureFirstPersonPlural)
      .perfectiveFutureSecondPersonPlural(perfectiveFutureSecondPersonPlural)
      .perfectiveFutureThirdPersonPlural(perfectiveFutureThirdPersonPlural)
      .build();
  }

}
