package es.pausegarra.russkiy_po_moyemu.vocabulary.application.services.update_word;

import es.pausegarra.russkiy_po_moyemu.vocabulary.domain.enums.WordTypes;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record UpdateWordDto(
  @NotNull UUID id,
  @NotBlank String russian,
  @NotBlank String english,
  @NotBlank String spanish,
  @NotNull WordTypes type
) {

  public static UpdateWordDto from(String id, String russian, String english, String spanish, String type) {
    return new UpdateWordDto(UUID.fromString(id), russian, english, spanish, WordTypes.valueOf(type));
  }

}
