package es.pausegarra.russian_cheatsheet.context.words.infrastructure.presentations;

import es.pausegarra.russian_cheatsheet.context.words.application.dto.WordDto;
import es.pausegarra.russian_cheatsheet.context.words.application.dto.WordTranslationDto;
import es.pausegarra.russian_cheatsheet.context.words.domain.enums.WordAspect;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public record ListWordsPresentation(
  UUID id,
  String externalId,
  String russian,
  List<WordTranslationDto> translations,
  String type,
  WordAspect aspect,
  String checksum
) {

  public static ListWordsPresentation fromDto(WordDto dto) {
    String type = Optional.ofNullable(dto.type())
      .map(value -> value.value())
      .orElse(null);

    return new ListWordsPresentation(
      dto.id(), dto.externalId(), dto.russian(), dto.translations(), type, dto.aspect(), dto.checksum()
    );
  }

}
