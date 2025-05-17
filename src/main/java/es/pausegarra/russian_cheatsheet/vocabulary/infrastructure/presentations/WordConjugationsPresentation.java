package es.pausegarra.russian_cheatsheet.vocabulary.infrastructure.presentations;

import es.pausegarra.russian_cheatsheet.vocabulary.application.dto.ConjugationDto;

public record WordConjugationsPresentation(
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

  public static WordConjugationsPresentation fromDto(ConjugationDto dto) {
    return new WordConjugationsPresentation(
      dto.imperfectivePresentFirstPersonSingular(),
      dto.imperfectivePresentSecondPersonSingular(),
      dto.imperfectivePresentThirdPersonSingular(),
      dto.imperfectivePresentFirstPersonPlural(),
      dto.imperfectivePresentSecondPersonPlural(),
      dto.imperfectivePresentThirdPersonPlural(),

      dto.imperfectivePastMasculine(),
      dto.imperfectivePastFeminine(),
      dto.imperfectivePastNeuter(),
      dto.imperfectivePastPlural(),

      dto.imperfectiveFutureFirstPersonSingular(),
      dto.imperfectiveFutureSecondPersonSingular(),
      dto.imperfectiveFutureThirdPersonSingular(),
      dto.imperfectiveFutureFirstPersonPlural(),
      dto.imperfectiveFutureSecondPersonPlural(),
      dto.imperfectiveFutureThirdPersonPlural(),

      dto.perfectivePresentFirstPersonSingular(),
      dto.perfectivePresentSecondPersonSingular(),
      dto.perfectivePresentThirdPersonSingular(),
      dto.perfectivePresentFirstPersonPlural(),
      dto.perfectivePresentSecondPersonPlural(),
      dto.perfectivePresentThirdPersonPlural(),

      dto.perfectivePastMasculine(),
      dto.perfectivePastFeminine(),
      dto.perfectivePastNeuter(),
      dto.perfectivePastPlural(),

      dto.perfectiveFutureFirstPersonSingular(),
      dto.perfectiveFutureSecondPersonSingular(),
      dto.perfectiveFutureThirdPersonSingular(),
      dto.perfectiveFutureFirstPersonPlural(),
      dto.perfectiveFutureSecondPersonPlural(),
      dto.perfectiveFutureThirdPersonPlural()
    );
  }

}
