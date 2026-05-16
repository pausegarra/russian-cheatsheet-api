package es.pausegarra.russian_cheatsheet.context.words.domain.entities;

import lombok.Builder;

@Builder
public record WordDeclinationEntity(
  String nominative,
  String accusative,
  String genitive,
  String dative,
  String instrumental,
  String prepositional,
  String nominativePlural,
  String accusativePlural,
  String genitivePlural,
  String dativePlural,
  String instrumentalPlural,
  String prepositionalPlural
) {

  public static WordDeclinationEntity create(
    String nominative,
    String accusative,
    String genitive,
    String dative,
    String instrumental,
    String prepositional,
    String nominativePlural,
    String accusativePlural,
    String genitivePlural,
    String dativePlural,
    String instrumentalPlural,
    String prepositionalPlural
  ) {
    return WordDeclinationEntity.builder()
      .nominative(nominative)
      .accusative(accusative)
      .genitive(genitive)
      .dative(dative)
      .instrumental(instrumental)
      .prepositional(prepositional)
      .nominativePlural(nominativePlural)
      .accusativePlural(accusativePlural)
      .genitivePlural(genitivePlural)
      .dativePlural(dativePlural)
      .instrumentalPlural(instrumentalPlural)
      .prepositionalPlural(prepositionalPlural)
      .build();
  }

}
