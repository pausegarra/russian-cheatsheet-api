package es.pausegarra.russian_cheatsheet.vocabulary.application.dto;

import es.pausegarra.russian_cheatsheet.vocabulary.domain.entities.WordCasesEntity;
import lombok.Builder;

@Builder
public record WordCaseDto(
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

  public static WordCaseDto fromEntity(WordCasesEntity entity) {
    return new WordCaseDto(
      entity.getNominativeMasculine(),
      entity.getNominativeFeminine(),
      entity.getNominativeNeuter(),
      entity.getGenitiveMasculine(),
      entity.getGenitiveFeminine(),
      entity.getGenitiveNeuter(),
      entity.getDativeMasculine(),
      entity.getDativeFeminine(),
      entity.getDativeNeuter(),
      entity.getAccusativeMasculine(),
      entity.getAccusativeFeminine(),
      entity.getAccusativeNeuter(),
      entity.getInstrumentalMasculine(),
      entity.getInstrumentalFeminine(),
      entity.getInstrumentalNeuter(),
      entity.getPrepositionalMasculine(),
      entity.getPrepositionalFeminine(),
      entity.getPrepositionalNeuter()
    );
  }

}
