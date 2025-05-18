package es.pausegarra.russian_cheatsheet.vocabulary.infrastructure.presentations;

import es.pausegarra.russian_cheatsheet.vocabulary.application.dto.WordCaseDto;
import lombok.Builder;

@Builder
public record WordCasePresentation(
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

  public static WordCasePresentation fromDto(WordCaseDto dto) {
    return new WordCasePresentation(
      dto.nominativeMasculine(),
      dto.nominativeFeminine(),
      dto.nominativeNeuter(),
      dto.genitiveMasculine(),
      dto.genitiveFeminine(),
      dto.genitiveNeuter(),
      dto.dativeMasculine(),
      dto.dativeFeminine(),
      dto.dativeNeuter(),
      dto.accusativeMasculine(),
      dto.accusativeFeminine(),
      dto.accusativeNeuter(),
      dto.instrumentalMasculine(),
      dto.instrumentalFeminine(),
      dto.instrumentalNeuter(),
      dto.prepositionalMasculine(),
      dto.prepositionalFeminine(),
      dto.prepositionalNeuter()
    );
  }

}
