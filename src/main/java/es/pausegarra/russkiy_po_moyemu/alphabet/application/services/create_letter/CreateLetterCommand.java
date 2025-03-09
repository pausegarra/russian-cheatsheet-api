package es.pausegarra.russkiy_po_moyemu.alphabet.application.services.create_letter;

import jakarta.validation.constraints.NotBlank;

public record CreateLetterCommand(
    @NotBlank String cyrillic,
    @NotBlank String ipa,
    @NotBlank String latin
) {

  public static CreateLetterCommand from(String cyrillic, String ipa, String latin) {
    return new CreateLetterCommand(cyrillic, ipa, latin);
  }

}
