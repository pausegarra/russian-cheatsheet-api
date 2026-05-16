package es.pausegarra.russian_cheatsheet.context.words.infrastructure.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public record WordConjugationJson(
  @JsonProperty("imperfective_present_first_person_singular") String imperfectivePresentFirstPersonSingular,
  @JsonProperty("imperfective_present_second_person_singular") String imperfectivePresentSecondPersonSingular,
  @JsonProperty("imperfective_present_third_person_singular") String imperfectivePresentThirdPersonSingular,
  @JsonProperty("imperfective_present_first_person_plural") String imperfectivePresentFirstPersonPlural,
  @JsonProperty("imperfective_present_second_person_plural") String imperfectivePresentSecondPersonPlural,
  @JsonProperty("imperfective_present_third_person_plural") String imperfectivePresentThirdPersonPlural,
  @JsonProperty("imperfective_past_masculine") String imperfectivePastMasculine,
  @JsonProperty("imperfective_past_feminine") String imperfectivePastFeminine,
  @JsonProperty("imperfective_past_neuter") String imperfectivePastNeuter,
  @JsonProperty("imperfective_past_plural") String imperfectivePastPlural,
  @JsonProperty("imperfective_future_first_person_singular") String imperfectiveFutureFirstPersonSingular,
  @JsonProperty("imperfective_future_second_person_singular") String imperfectiveFutureSecondPersonSingular,
  @JsonProperty("imperfective_future_third_person_singular") String imperfectiveFutureThirdPersonSingular,
  @JsonProperty("imperfective_future_first_person_plural") String imperfectiveFutureFirstPersonPlural,
  @JsonProperty("imperfective_future_second_person_plural") String imperfectiveFutureSecondPersonPlural,
  @JsonProperty("imperfective_future_third_person_plural") String imperfectiveFutureThirdPersonPlural,
  @JsonProperty("perfective_past_masculine") String perfectivePastMasculine,
  @JsonProperty("perfective_past_feminine") String perfectivePastFeminine,
  @JsonProperty("perfective_past_neuter") String perfectivePastNeuter,
  @JsonProperty("perfective_past_plural") String perfectivePastPlural,
  @JsonProperty("perfective_future_first_person_singular") String perfectiveFutureFirstPersonSingular,
  @JsonProperty("perfective_future_second_person_singular") String perfectiveFutureSecondPersonSingular,
  @JsonProperty("perfective_future_third_person_singular") String perfectiveFutureThirdPersonSingular,
  @JsonProperty("perfective_future_first_person_plural") String perfectiveFutureFirstPersonPlural,
  @JsonProperty("perfective_future_second_person_plural") String perfectiveFutureSecondPersonPlural,
  @JsonProperty("perfective_future_third_person_plural") String perfectiveFutureThirdPersonPlural
) {
}
