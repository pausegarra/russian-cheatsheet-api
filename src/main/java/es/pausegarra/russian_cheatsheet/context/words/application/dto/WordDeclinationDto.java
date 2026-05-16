package es.pausegarra.russian_cheatsheet.context.words.application.dto;

import es.pausegarra.russian_cheatsheet.context.words.domain.entities.WordDeclinationEntity;

public record WordDeclinationDto(
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

  public static WordDeclinationDto fromEntity(WordDeclinationEntity entity) {
    return new WordDeclinationDto(
      entity.nominative(),
      entity.accusative(),
      entity.genitive(),
      entity.dative(),
      entity.instrumental(),
      entity.prepositional(),
      entity.nominativePlural(),
      entity.accusativePlural(),
      entity.genitivePlural(),
      entity.dativePlural(),
      entity.instrumentalPlural(),
      entity.prepositionalPlural()
    );
  }

}
