package es.pausegarra.russian_cheatsheet.context.words.application.dto;

import es.pausegarra.russian_cheatsheet.context.words.domain.entities.WordConjugationEntity;

public record WordConjugationDto(
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

  public static WordConjugationDto fromEntity(WordConjugationEntity entity) {
    return new WordConjugationDto(
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

}
