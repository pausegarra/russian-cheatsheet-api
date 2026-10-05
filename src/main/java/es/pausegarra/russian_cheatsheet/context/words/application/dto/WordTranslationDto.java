package es.pausegarra.russian_cheatsheet.context.words.application.dto;

import es.pausegarra.russian_cheatsheet.context.words.domain.entities.WordTranslationEntity;

public record WordTranslationDto(
  String language,
  String text,
  int position
) {

  public static WordTranslationDto fromEntity(WordTranslationEntity entity) {
    return new WordTranslationDto(entity.language(), entity.text(), entity.position());
  }

}
