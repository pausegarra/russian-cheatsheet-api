package es.pausegarra.russian_cheatsheet.context.words.application.dto;

import es.pausegarra.russian_cheatsheet.context.words.domain.entities.ExampleTranslationEntity;

public record ExampleTranslationDto(
  String language,
  String text,
  int position
) {

  public static ExampleTranslationDto fromEntity(ExampleTranslationEntity entity) {
    return new ExampleTranslationDto(entity.language(), entity.text(), entity.position());
  }

}
