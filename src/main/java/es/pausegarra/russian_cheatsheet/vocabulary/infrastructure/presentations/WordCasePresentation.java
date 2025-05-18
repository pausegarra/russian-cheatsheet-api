package es.pausegarra.russian_cheatsheet.vocabulary.infrastructure.presentations;

import es.pausegarra.russian_cheatsheet.vocabulary.application.dto.WordCasesDto;
import lombok.Builder;

@Builder
public record WordCasePresentation(
  String nominative,
  String genitive,
  String dative,
  String accusative,
  String instrumental,
  String prepositional
) {

  public static WordCasePresentation fromDto(WordCasesDto dto) {
    return new WordCasePresentation(
      dto.nominative(),
      dto.genitive(),
      dto.dative(),
      dto.accusative(),
      dto.instrumental(),
      dto.prepositional()
    );
  }

}
