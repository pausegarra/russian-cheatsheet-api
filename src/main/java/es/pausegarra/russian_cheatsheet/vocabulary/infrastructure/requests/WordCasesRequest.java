package es.pausegarra.russian_cheatsheet.vocabulary.infrastructure.requests;

import es.pausegarra.russian_cheatsheet.vocabulary.application.dto.WordCasesDto;
import lombok.Builder;

@Builder
public record WordCasesRequest(
  String nominativeMasculine,
  String nominativeFeminine,
  String nominativeNeuter,
  String genitiveMasculine,
  String genitiveFeminine,
  String genitiveNeuter,
  String dativeMasculine,
  String dativeFeminine,
  String dativeNeuter,
  String accusativeMasculine,
  String accusativeFeminine,
  String accusativeNeuter,
  String instrumentalMasculine,
  String instrumentalFeminine,
  String instrumentalNeuter,
  String prepositionalMasculine,
  String prepositionalFeminine,
  String prepositionalNeuter
) {

  public WordCasesDto toDto() {
    return new WordCasesDto(
      nominativeMasculine(),
      nominativeFeminine(),
      nominativeNeuter(),
      genitiveMasculine(),
      genitiveFeminine(),
      genitiveNeuter(),
      dativeMasculine(),
      dativeFeminine(),
      dativeNeuter(),
      accusativeMasculine(),
      accusativeFeminine(),
      accusativeNeuter(),
      instrumentalMasculine(),
      instrumentalFeminine(),
      instrumentalNeuter(),
      prepositionalMasculine(),
      prepositionalFeminine(),
      prepositionalNeuter()
    );
  }

}
