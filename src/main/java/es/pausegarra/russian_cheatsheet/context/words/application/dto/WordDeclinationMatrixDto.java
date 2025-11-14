package es.pausegarra.russian_cheatsheet.context.words.application.dto;

import es.pausegarra.russian_cheatsheet.context.words.domain.entities.WordDeclinationMatrixEntity;

import java.time.Instant;
import java.util.UUID;

public record WordDeclinationMatrixDto(
  UUID id,
  WordDto word,
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

  public static WordDeclinationMatrixDto fromEntity(WordDeclinationMatrixEntity entity, WordDto word) {
    return new WordDeclinationMatrixDto(
      entity.id(),
      word,
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
      entity.prepositionalPlural(),
      entity.createdBy(),
      entity.createdAt(),
      entity.updatedBy(),
      entity.updatedAt()
    );
  }

}
