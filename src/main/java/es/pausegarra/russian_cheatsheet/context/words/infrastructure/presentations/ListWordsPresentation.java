package es.pausegarra.russian_cheatsheet.context.words.infrastructure.presentations;

import es.pausegarra.russian_cheatsheet.context.words.application.dto.WordDto;

import java.util.Optional;
import java.util.UUID;

public record ListWordsPresentation(
  UUID id, String russian, String english, String spanish, String type
) {

  public static ListWordsPresentation fromDto(WordDto dto) {
    String type = Optional.ofNullable(dto.type())
      .map(Enum::name)
      .orElse(null);

    return new ListWordsPresentation(
      dto.id(), dto.russian(), dto.english(), dto.spanish(), type
    );
  }

}
