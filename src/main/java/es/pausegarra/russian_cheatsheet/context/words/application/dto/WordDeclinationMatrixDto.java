package es.pausegarra.russian_cheatsheet.context.words.application.dto;

import es.pausegarra.russian_cheatsheet.context.words.domain.entities.WordDeclinationMatrixEntity;

public record WordDeclinationMatrixDto(
  String nominativeMasculine,
  String nominativeFeminine,
  String nominativeNeuter,
  String nominativePlural,
  String accusativeMasculine,
  String accusativeFeminine,
  String accusativeNeuter,
  String accusativePlural,
  String genitiveMasculine,
  String genitiveFeminine,
  String genitiveNeuter,
  String genitivePlural,
  String dativeMasculine,
  String dativeFeminine,
  String dativeNeuter,
  String dativePlural,
  String instrumentalMasculine,
  String instrumentalFeminine,
  String instrumentalNeuter,
  String instrumentalPlural,
  String prepositionalMasculine,
  String prepositionalFeminine,
  String prepositionalNeuter,
  String prepositionalPlural
) {

  public static WordDeclinationMatrixDto fromEntity(WordDeclinationMatrixEntity entity) {
    return new WordDeclinationMatrixDto(
      entity.nominativeMasculine(),
      entity.nominativeFeminine(),
      entity.nominativeNeuter(),
      entity.nominativePlural(),
      entity.accusativeMasculine(),
      entity.accusativeFeminine(),
      entity.accusativeNeuter(),
      entity.accusativePlural(),
      entity.genitiveMasculine(),
      entity.genitiveFeminine(),
      entity.genitiveNeuter(),
      entity.genitivePlural(),
      entity.dativeMasculine(),
      entity.dativeFeminine(),
      entity.dativeNeuter(),
      entity.dativePlural(),
      entity.instrumentalMasculine(),
      entity.instrumentalFeminine(),
      entity.instrumentalNeuter(),
      entity.instrumentalPlural(),
      entity.prepositionalMasculine(),
      entity.prepositionalFeminine(),
      entity.prepositionalNeuter(),
      entity.prepositionalPlural()
    );
  }

}
