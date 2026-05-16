package es.pausegarra.russian_cheatsheet.context.words.infrastructure.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public record WordDeclinationJson(
  @JsonProperty("nominative") String nominative,
  @JsonProperty("accusative") String accusative,
  @JsonProperty("genitive") String genitive,
  @JsonProperty("dative") String dative,
  @JsonProperty("instrumental") String instrumental,
  @JsonProperty("prepositional") String prepositional,
  @JsonProperty("nominative_plural") String nominativePlural,
  @JsonProperty("accusative_plural") String accusativePlural,
  @JsonProperty("genitive_plural") String genitivePlural,
  @JsonProperty("dative_plural") String dativePlural,
  @JsonProperty("instrumental_plural") String instrumentalPlural,
  @JsonProperty("prepositional_plural") String prepositionalPlural
) {
}
