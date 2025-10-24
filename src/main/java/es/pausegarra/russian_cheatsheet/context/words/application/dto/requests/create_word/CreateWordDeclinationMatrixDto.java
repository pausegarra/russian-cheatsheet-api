package es.pausegarra.russian_cheatsheet.context.words.application.dto.requests.create_word;

public record CreateWordDeclinationMatrixDto(
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
) {}
