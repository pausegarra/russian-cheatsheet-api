package es.pausegarra.russian_cheatsheet.context.words.application.use_cases.create_word;

import es.pausegarra.russian_cheatsheet.context.words.domain.enums.WordType;

public record CreateWordDto(
  String russian,
  String english,
  String spanish,
  WordType type,
  CreateWordConjugationDto conjugations,
  CreateWordDeclinationDto declinations,
  CreateWordDeclinationMatrixDto declinationMatrix
) {}
