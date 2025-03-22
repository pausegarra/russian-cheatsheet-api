package es.pausegarra.russkiy_po_moyemu.alphabet.infrastructure.projections;

import es.pausegarra.russkiy_po_moyemu.alphabet.application.dtos.LetterDto;

import java.util.UUID;

public record LetterProjection(
    UUID id,
    String cyrillic,
    String latin,
    String ipa
) {

  public static LetterProjection fromDto(LetterDto dto) {
    return new LetterProjection(
        dto.id(),
        dto.cyrillic(),
        dto.latin(),
        dto.ipa()
    );
  }

}
