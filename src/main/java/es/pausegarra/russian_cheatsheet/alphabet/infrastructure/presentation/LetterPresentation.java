package es.pausegarra.russian_cheatsheet.alphabet.infrastructure.presentation;

import es.pausegarra.russian_cheatsheet.alphabet.application.dtos.LetterDto;

import java.util.UUID;

public record LetterPresentation(
  UUID id, String cyrillic, String latin, String ipa
) {

  public static LetterPresentation fromDto(LetterDto dto) {
    return new LetterPresentation(dto.id(), dto.cyrillic(), dto.latin(), dto.ipa());
  }

}
