package es.pausegarra.russian_cheatsheet.context.words.application.use_cases.update_word;

import es.pausegarra.russian_cheatsheet.context.words.domain.enums.WordType;

import java.util.UUID;

public record UpdateWordDto(
  UUID id,
  String russian,
  String spanish,
  String english,
  WordType type,
  UpdateWordDeclinationDto declinations,
  UpdateWordConjugationsDto conjugations,
  UpdateWordDeclinationMatrixDto declinationMatrix
) {

  public static UpdateWordDto from(
    String id,
    String russian,
    String spanish,
    String english,
    String type,
    UpdateWordDeclinationDto declinations,
    UpdateWordConjugationsDto conjugations,
    UpdateWordDeclinationMatrixDto declinationMatrix
  ) {
    return new UpdateWordDto(
      UUID.fromString(id),
      russian,
      spanish,
      english,
      WordType.valueOf(type),
      declinations,
      conjugations,
      declinationMatrix
    );
  }

}
