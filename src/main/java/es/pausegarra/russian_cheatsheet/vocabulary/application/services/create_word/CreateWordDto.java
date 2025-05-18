package es.pausegarra.russian_cheatsheet.vocabulary.application.services.create_word;

import es.pausegarra.russian_cheatsheet.vocabulary.application.dto.WordCasesDto;
import es.pausegarra.russian_cheatsheet.vocabulary.application.dto.WordConjugationsDto;
import es.pausegarra.russian_cheatsheet.vocabulary.domain.enums.WordTypes;
import jakarta.validation.constraints.NotBlank;

public record CreateWordDto(
  @NotBlank String russian,
  @NotBlank String english,
  @NotBlank String spanish,
  WordTypes type,
  WordConjugationsDto conjugations,
  WordCasesDto cases
) {

  public static CreateWordDto from(
    String russian,
    String english,
    String spanish,
    String type,
    WordConjugationsDto conjugations,
    WordCasesDto cases
  ) {
    return new CreateWordDto(
      russian,
      english,
      spanish,
      WordTypes.valueOf(type.toUpperCase()),
      conjugations,
      cases
    );
  }

}
