package es.pausegarra.russian_cheatsheet.vocabulary.infrastructure.requests;

import es.pausegarra.russian_cheatsheet.vocabulary.application.dto.WordCasesDto;
import lombok.Builder;

@Builder
public record WordCasesRequest(
  String nominative,
  String genitive,
  String dative,
  String accusative,
  String instrumental,
  String prepositional
) {

  public WordCasesDto toDto() {
    return new WordCasesDto(
      nominative(),
      genitive(),
      dative(),
      accusative(),
      instrumental(),
      prepositional()
    );
  }

}
