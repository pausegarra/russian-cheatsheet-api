package es.pausegarra.russian_cheatsheet.vocabulary.application.dto;

import es.pausegarra.russian_cheatsheet.vocabulary.domain.entities.WordCasesEntity;

public record WordCasesDto(
  String nominative,
  String genitive,
  String dative,
  String accusative,
  String instrumental,
  String prepositional
) {

  public static WordCasesDto from(
    String nominative,
    String genitive,
    String dative,
    String accusative,
    String instrumental,
    String prepositional
  ) {
    return new WordCasesDto(
      nominative,
      genitive,
      dative,
      accusative,
      instrumental,
      prepositional
    );
  }

  public WordCasesEntity toEntity() {
    return new WordCasesEntity(
      null,
      null,
      nominative,
      genitive,
      dative,
      accusative,
      instrumental,
      prepositional
    );
  }

  public static WordCasesDto fromEntity(WordCasesEntity entity) {
    return new WordCasesDto(
      entity.getNominative(),
      entity.getGenitive(),
      entity.getDative(),
      entity.getAccusative(),
      entity.getInstrumental(),
      entity.getPrepositional()
    );
  }

}
