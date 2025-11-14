package es.pausegarra.russian_cheatsheet.context.words.infrastructure.presentations;

import es.pausegarra.russian_cheatsheet.context.words.application.dto.WordDto;

import java.util.UUID;

public record WordPresentation(
  UUID id,
  String russian,
  String english,
  String spanish,
  String type
) {

  public static WordPresentation fromDto(WordDto dto) {
    return new WordPresentation(
      dto.id(),
      dto.russian(),
      dto.english(),
      dto.spanish(),
//      dto.type().name()
      null
    );
  }

}
