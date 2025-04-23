package es.pausegarra.russian_cheatsheet.alphabet.application.services.create_letter;

import jakarta.validation.constraints.NotBlank;

public record CreateLetterDto(
  @NotBlank String cyrillic, @NotBlank String ipa, @NotBlank String latin
) {

  public static CreateLetterDto from(String cyrillic, String ipa, String latin) {
    return new CreateLetterDto(cyrillic, ipa, latin);
  }

}
