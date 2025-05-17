package es.pausegarra.russian_cheatsheet.vocabulary.application.dto;

import es.pausegarra.russian_cheatsheet.vocabulary.domain.entities.VerbConjugationEntity;

public record ConjugationDto(
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

  String perfectivePresentFirstPersonSingular,
  String perfectivePresentSecondPersonSingular,
  String perfectivePresentThirdPersonSingular,
  String perfectivePresentFirstPersonPlural,
  String perfectivePresentSecondPersonPlural,
  String perfectivePresentThirdPersonPlural,

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

  public static ConjugationDto from(VerbConjugationEntity entity) {
    return new ConjugationDto(
      entity.getImperfectivePresentFirstPersonSingular(),
      entity.getImperfectivePresentSecondPersonSingular(),
      entity.getImperfectivePresentThirdPersonSingular(),
      entity.getImperfectivePresentFirstPersonPlural(),
      entity.getImperfectivePresentSecondPersonPlural(),
      entity.getImperfectivePresentThirdPersonPlural(),

      entity.getImperfectivePastMasculine(),
      entity.getImperfectivePastFeminine(),
      entity.getImperfectivePastNeuter(),
      entity.getImperfectivePastPlural(),

      entity.getImperfectiveFutureFirstPersonSingular(),
      entity.getImperfectiveFutureSecondPersonSingular(),
      entity.getImperfectiveFutureThirdPersonSingular(),
      entity.getImperfectiveFutureFirstPersonPlural(),
      entity.getImperfectiveFutureSecondPersonPlural(),
      entity.getImperfectiveFutureThirdPersonPlural(),

      entity.getPerfectivePresentFirstPersonSingular(),
      entity.getPerfectivePresentSecondPersonSingular(),
      entity.getPerfectivePresentThirdPersonSingular(),
      entity.getPerfectivePresentFirstPersonPlural(),
      entity.getPerfectivePresentSecondPersonPlural(),
      entity.getPerfectivePresentThirdPersonPlural(),

      entity.getPerfectivePastMasculine(),
      entity.getPerfectivePastFeminine(),
      entity.getPerfectivePastNeuter(),
      entity.getPerfectivePastPlural(),

      entity.getPerfectiveFutureFirstPersonSingular(),
      entity.getPerfectiveFutureSecondPersonSingular(),
      entity.getPerfectiveFutureThirdPersonSingular(),
      entity.getPerfectiveFutureFirstPersonPlural(),
      entity.getPerfectiveFutureSecondPersonPlural(),
      entity.getPerfectiveFutureThirdPersonPlural()
    );
  }

}
