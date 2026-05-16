package es.pausegarra.russian_cheatsheet.context.words.infrastructure.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public record WordDeclinationMatrixJson(
  @JsonProperty("nominative_masculine") String nominativeMasculine,
  @JsonProperty("nominative_feminine") String nominativeFeminine,
  @JsonProperty("nominative_neuter") String nominativeNeuter,
  @JsonProperty("nominative_plural") String nominativePlural,
  @JsonProperty("accusative_masculine") String accusativeMasculine,
  @JsonProperty("accusative_feminine") String accusativeFeminine,
  @JsonProperty("accusative_neuter") String accusativeNeuter,
  @JsonProperty("accusative_plural") String accusativePlural,
  @JsonProperty("genitive_masculine") String genitiveMasculine,
  @JsonProperty("genitive_feminine") String genitiveFeminine,
  @JsonProperty("genitive_neuter") String genitiveNeuter,
  @JsonProperty("genitive_plural") String genitivePlural,
  @JsonProperty("dative_masculine") String dativeMasculine,
  @JsonProperty("dative_feminine") String dativeFeminine,
  @JsonProperty("dative_neuter") String dativeNeuter,
  @JsonProperty("dative_plural") String dativePlural,
  @JsonProperty("instrumental_masculine") String instrumentalMasculine,
  @JsonProperty("instrumental_feminine") String instrumentalFeminine,
  @JsonProperty("instrumental_neuter") String instrumentalNeuter,
  @JsonProperty("instrumental_plural") String instrumentalPlural,
  @JsonProperty("prepositional_masculine") String prepositionalMasculine,
  @JsonProperty("prepositional_feminine") String prepositionalFeminine,
  @JsonProperty("prepositional_neuter") String prepositionalNeuter,
  @JsonProperty("prepositional_plural") String prepositionalPlural
) {
}
