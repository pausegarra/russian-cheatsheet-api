package es.pausegarra.russian_cheatsheet.vocabulary.application.services.update_word;

import es.pausegarra.russian_cheatsheet.vocabulary.application.dto.WordCasesDto;
import es.pausegarra.russian_cheatsheet.vocabulary.application.dto.WordConjugationsDto;
import es.pausegarra.russian_cheatsheet.vocabulary.domain.enums.WordTypes;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record UpdateWordDto(
  @NotNull UUID id,
  @NotBlank String russian,
  @NotBlank String english,
  @NotBlank String spanish,
  @NotNull WordTypes type,
  WordConjugationsDto conjugations,
  WordCasesDto cases
) {

  public static UpdateWordDto from(
    String id,
    String russian,
    String english,
    String spanish,
    String type,
    WordConjugationsDto conjugations,
    WordCasesDto cases
  ) {
    return new UpdateWordDto(
      UUID.fromString(id),
      russian,
      english,
      spanish,
      WordTypes.valueOf(type),
      conjugations,
      cases
    );
  }

}
