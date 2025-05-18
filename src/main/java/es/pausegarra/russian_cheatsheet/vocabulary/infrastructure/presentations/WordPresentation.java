package es.pausegarra.russian_cheatsheet.vocabulary.infrastructure.presentations;

import es.pausegarra.russian_cheatsheet.vocabulary.application.dto.WordDto;

public record WordPresentation(
  String id,
  String russian,
  String english,
  String spanish,
  String type,
  WordConjugationsPresentation conjugations,
  WordCasePresentation cases
) {

  public static WordPresentation fromDto(WordDto dto) {
    WordConjugationsPresentation conjugations = null;
    WordCasePresentation cases = null;

    if (dto.conjugations() != null) {
      conjugations = WordConjugationsPresentation.fromDto(dto.conjugations());
    }

    if (dto.cases() != null) {
      cases = WordCasePresentation.fromDto(dto.cases());
    }

    return new WordPresentation(
      dto.id()
        .toString(), dto.russian(), dto.english(), dto.spanish(), dto.type(), conjugations, cases
    );
  }

}
