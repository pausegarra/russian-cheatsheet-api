package es.pausegarra.russian_cheatsheet.vocabulary.application.services.create_word;

import jakarta.validation.constraints.NotBlank;

public record WordConjugationsDto(
  @NotBlank String imperfectivePresentFirstPersonSingular,
  @NotBlank String imperfectivePresentSecondPersonSingular,
  @NotBlank String imperfectivePresentThirdPersonSingular,
  @NotBlank String imperfectivePresentFirstPersonPlural,
  @NotBlank String imperfectivePresentSecondPersonPlural,
  @NotBlank String imperfectivePresentThirdPersonPlural,

  @NotBlank String imperfectivePastMasculine,
  @NotBlank String imperfectivePastFeminine,
  @NotBlank String imperfectivePastNeuter,
  @NotBlank String imperfectivePastPlural,

  @NotBlank String imperfectiveFutureFirstPersonSingular,
  @NotBlank String imperfectiveFutureSecondPersonSingular,
  @NotBlank String imperfectiveFutureThirdPersonSingular,
  @NotBlank String imperfectiveFutureFirstPersonPlural,
  @NotBlank String imperfectiveFutureSecondPersonPlural,
  @NotBlank String imperfectiveFutureThirdPersonPlural,

  String perfectivePresentFirstPersonSingular,
  String perfectivePresentSecondPersonSingular,
  String perfectivePresentThirdPersonSingular,
  String perfectivePresentFirstPersonPlural,
  String perfectivePresentSecondPersonPlural,
  String perfectivePresentThirdPersonPlural,

  @NotBlank String perfectivePastMasculine,
  @NotBlank String perfectivePastFeminine,
  @NotBlank String perfectivePastNeuter,
  @NotBlank String perfectivePastPlural,

  @NotBlank String perfectiveFutureFirstPersonSingular,
  @NotBlank String perfectiveFutureSecondPersonSingular,
  @NotBlank String perfectiveFutureThirdPersonSingular,
  @NotBlank String perfectiveFutureFirstPersonPlural,
  @NotBlank String perfectiveFutureSecondPersonPlural,
  @NotBlank String perfectiveFutureThirdPersonPlural

) {

  public static WordConjugationsDto from(
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
    return new WordConjugationsDto(
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
