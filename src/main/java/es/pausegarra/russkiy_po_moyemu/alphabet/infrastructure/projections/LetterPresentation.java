package es.pausegarra.russkiy_po_moyemu.alphabet.infrastructure.projections;

import es.pausegarra.russkiy_po_moyemu.alphabet.application.dtos.LetterDto;

import java.util.UUID;

public record LetterPresentation(
  UUID id, String cyrillic, String latin, String ipa
) {

  public static LetterPresentation fromDto(LetterDto dto) {
    return new LetterPresentation(dto.id(), dto.cyrillic(), dto.latin(), dto.ipa());
  }

}
