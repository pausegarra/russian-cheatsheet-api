package es.pausegarra.russian_cheatsheet.context.words.domain.entities;

import lombok.Builder;

import java.time.Instant;
import java.util.UUID;

@Builder
public record WordDeclinationMatrixEntity(
  UUID id,
  WordEntity word,
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
  String prepositionalPlural,
  String createdBy,
  Instant createdAt,
  String updatedBy,
  Instant updatedAt
) {

  public static WordDeclinationMatrixEntity create(
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
    return WordDeclinationMatrixEntity.builder()
      .nominativeMasculine(nominativeMasculine)
      .nominativeFeminine(nominativeFeminine)
      .nominativeNeuter(nominativeNeuter)
      .nominativePlural(nominativePlural)
      .accusativeMasculine(accusativeMasculine)
      .accusativeFeminine(accusativeFeminine)
      .accusativeNeuter(accusativeNeuter)
      .accusativePlural(accusativePlural)
      .genitiveMasculine(genitiveMasculine)
      .genitiveFeminine(genitiveFeminine)
      .genitiveNeuter(genitiveNeuter)
      .genitivePlural(genitivePlural)
      .dativeMasculine(dativeMasculine)
      .dativeFeminine(dativeFeminine)
      .dativeNeuter(dativeNeuter)
      .dativePlural(dativePlural)
      .instrumentalMasculine(instrumentalMasculine)
      .instrumentalFeminine(instrumentalFeminine)
      .instrumentalNeuter(instrumentalNeuter)
      .instrumentalPlural(instrumentalPlural)
      .prepositionalMasculine(prepositionalMasculine)
      .prepositionalFeminine(prepositionalFeminine)
      .prepositionalNeuter(prepositionalNeuter)
      .prepositionalPlural(prepositionalPlural)
      .build();
  }

}
