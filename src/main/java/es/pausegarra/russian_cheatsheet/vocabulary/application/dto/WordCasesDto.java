package es.pausegarra.russian_cheatsheet.vocabulary.application.dto;

import es.pausegarra.russian_cheatsheet.vocabulary.domain.entities.WordCasesEntity;

public record WordCasesDto(
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

  public static WordCasesDto from(
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
    return new WordCasesDto(
      nominativeMasculine,
      nominativeFeminine,
      nominativeNeuter,
      genitiveMasculine,
      genitiveFeminine,
      genitiveNeuter,
      dativeMasculine,
      dativeFeminine,
      dativeNeuter,
      accusativeMasculine,
      accusativeFeminine,
      accusativeNeuter,
      instrumentalMasculine,
      instrumentalFeminine,
      instrumentalNeuter,
      prepositionalMasculine,
      prepositionalFeminine,
      prepositionalNeuter
    );
  }

  public WordCasesEntity toEntity() {
    return new WordCasesEntity(
      null,
      null,
      nominativeMasculine,
      nominativeFeminine,
      nominativeNeuter,
      genitiveMasculine,
      genitiveFeminine,
      genitiveNeuter,
      dativeMasculine,
      dativeFeminine,
      dativeNeuter,
      accusativeMasculine,
      accusativeFeminine,
      accusativeNeuter,
      instrumentalMasculine,
      instrumentalFeminine,
      instrumentalNeuter,
      prepositionalMasculine,
      prepositionalFeminine,
      prepositionalNeuter
    );
  }

}
