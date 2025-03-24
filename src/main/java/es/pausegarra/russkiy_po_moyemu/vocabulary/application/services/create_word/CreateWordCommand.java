package es.pausegarra.russkiy_po_moyemu.vocabulary.application.services.create_word;

import es.pausegarra.russkiy_po_moyemu.vocabulary.domain.enums.WordTypes;
import jakarta.validation.constraints.NotBlank;

public record CreateWordCommand(
    @NotBlank String russian,
    @NotBlank String english,
    @NotBlank String spanish,
    WordTypes type
) {

  public static CreateWordCommand from(String russian, String english, String spanish, String type) {
    return new CreateWordCommand(russian, english, spanish, WordTypes.valueOf(type.toUpperCase()));
  }

}
