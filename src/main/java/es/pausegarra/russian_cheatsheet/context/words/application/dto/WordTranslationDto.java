package es.pausegarra.russian_cheatsheet.context.words.application.dto;

import es.pausegarra.russian_cheatsheet.context.words.domain.entities.WordTranslationEntity;
import es.pausegarra.russian_cheatsheet.context.words.domain.enums.TranslationOrigin;

public record WordTranslationDto(
  String language,
  String text,
  TranslationOrigin managedBy,
  int position
) {

  public static WordTranslationDto fromEntity(WordTranslationEntity entity) {
    return new WordTranslationDto(entity.language(), entity.text(), entity.managedBy(), entity.position());
  }

}
