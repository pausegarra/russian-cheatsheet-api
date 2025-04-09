package es.pausegarra.russkiy_po_moyemu.vocabulary.application.services.update_word;

import es.pausegarra.russkiy_po_moyemu.vocabulary.domain.enums.WordTypes;

import java.util.UUID;

public record UpdateWordDto(
  UUID id,
  String russian,
  String english,
  String spanish,
  WordTypes type
) {

  public static UpdateWordDto from(String id, String russian, String english, String spanish, String type) {
    return new UpdateWordDto(UUID.fromString(id), russian, english, spanish, WordTypes.valueOf(type));
  }

}
