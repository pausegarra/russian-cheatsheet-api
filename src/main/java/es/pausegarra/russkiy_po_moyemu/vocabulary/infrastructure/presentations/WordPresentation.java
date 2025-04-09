package es.pausegarra.russkiy_po_moyemu.vocabulary.infrastructure.presentations;

import es.pausegarra.russkiy_po_moyemu.vocabulary.application.dto.WordDto;

public record WordPresentation(
  String id, String russian, String english, String spanish, String type
) {

  public static WordPresentation fromDto(WordDto dto) {
    return new WordPresentation(dto.id().toString(), dto.russian(), dto.english(), dto.spanish(), dto.type());
  }

}
