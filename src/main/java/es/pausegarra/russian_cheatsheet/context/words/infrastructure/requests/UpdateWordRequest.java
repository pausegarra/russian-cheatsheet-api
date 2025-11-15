package es.pausegarra.russian_cheatsheet.context.words.infrastructure.requests;

import es.pausegarra.russian_cheatsheet.context.words.application.use_cases.update_word.UpdateWordConjugationsDto;
import es.pausegarra.russian_cheatsheet.context.words.application.use_cases.update_word.UpdateWordDeclinationDto;
import es.pausegarra.russian_cheatsheet.context.words.application.use_cases.update_word.UpdateWordDeclinationMatrixDto;

public record UpdateWordRequest(
  String russian,
  String spanish,
  String english,
  String type,
  UpdateWordDeclinationDto declinations,
  UpdateWordDeclinationMatrixDto declinationMatrix,
  UpdateWordConjugationsDto conjugations
) {}
