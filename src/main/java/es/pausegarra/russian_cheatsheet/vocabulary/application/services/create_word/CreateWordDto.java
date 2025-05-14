package es.pausegarra.russian_cheatsheet.vocabulary.application.services.create_word;

import es.pausegarra.russian_cheatsheet.vocabulary.domain.enums.WordTypes;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

public record CreateWordDto(
  @NotBlank String russian,
  @NotBlank String english,
  @NotBlank String spanish,
  WordTypes type,
  WordConjugationsDto conjugations
) {

  public static CreateWordDto from(
    String russian, String english, String spanish, String type, WordConjugationsDto conjugations) {
    return new CreateWordDto(russian, english, spanish, WordTypes.valueOf(type.toUpperCase()), conjugations);
  }

}
