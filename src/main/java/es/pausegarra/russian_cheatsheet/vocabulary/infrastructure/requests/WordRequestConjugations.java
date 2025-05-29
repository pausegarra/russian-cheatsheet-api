package es.pausegarra.russian_cheatsheet.vocabulary.infrastructure.requests;

import es.pausegarra.russian_cheatsheet.vocabulary.application.dto.WordConjugationsDto;
import lombok.Builder;

@Builder
public record WordRequestConjugations(
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

  public WordConjugationsDto toDto() {
    return WordConjugationsDto.from(
      imperfectivePresentFirstPersonSingular,
      imperfectivePresentSecondPersonSingular,
      imperfectivePresentThirdPersonSingular,
      imperfectivePresentFirstPersonPlural,
      imperfectivePresentSecondPersonPlural,
      imperfectivePresentThirdPersonPlural,

      imperfectivePastMasculine,
      imperfectivePastFeminine,
      imperfectivePastNeuter,
      imperfectivePastPlural,

      imperfectiveFutureFirstPersonSingular,
      imperfectiveFutureSecondPersonSingular,
      imperfectiveFutureThirdPersonSingular,
      imperfectiveFutureFirstPersonPlural,
      imperfectiveFutureSecondPersonPlural,
      imperfectiveFutureThirdPersonPlural,

      perfectivePresentFirstPersonSingular,
      perfectivePresentSecondPersonSingular,
      perfectivePresentThirdPersonSingular,
      perfectivePresentFirstPersonPlural,
      perfectivePresentSecondPersonPlural,
      perfectivePresentThirdPersonPlural,

      perfectivePastMasculine,
      perfectivePastFeminine,
      perfectivePastNeuter,
      perfectivePastPlural,

      perfectiveFutureFirstPersonSingular,
      perfectiveFutureSecondPersonSingular,
      perfectiveFutureThirdPersonSingular,
      perfectiveFutureFirstPersonPlural,
      perfectiveFutureSecondPersonPlural,
      perfectiveFutureThirdPersonPlural
    );
  }

}
