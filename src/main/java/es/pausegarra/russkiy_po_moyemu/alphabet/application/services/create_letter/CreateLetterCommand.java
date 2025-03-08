package es.pausegarra.russkiy_po_moyemu.alphabet.application.services.create_letter;

import jakarta.validation.constraints.NotBlank;

public record CreateLetterCommand(
    @NotBlank String letter,
    @NotBlank String ipa
) {

  public static CreateLetterCommand from(String letter, String ipa) {
    return new CreateLetterCommand(letter, ipa);
  }

}
