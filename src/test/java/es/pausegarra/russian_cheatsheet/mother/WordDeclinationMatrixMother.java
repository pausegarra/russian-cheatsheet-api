package es.pausegarra.russian_cheatsheet.mother;

import es.pausegarra.russian_cheatsheet.context.words.domain.entities.WordDeclinationMatrixEntity;

import java.util.UUID;

public class WordDeclinationMatrixMother {

  public static WordDeclinationMatrixEntity.WordDeclinationMatrixEntityBuilder random() {
    return WordDeclinationMatrixEntity.builder()
      .id(UUID.randomUUID())
      .word(WordMother.random().build())
      .nominativeMasculine(MotherCreator.random().animal().name())
      .nominativeFeminine(MotherCreator.random().animal().name())
      .nominativeNeuter(MotherCreator.random().animal().name())
      .nominativePlural(MotherCreator.random().animal().name())
      .accusativeMasculine(MotherCreator.random().animal().name())
      .accusativeFeminine(MotherCreator.random().animal().name())
      .accusativeNeuter(MotherCreator.random().animal().name())
      .accusativePlural(MotherCreator.random().animal().name())
      .genitiveMasculine(MotherCreator.random().animal().name())
      .genitiveFeminine(MotherCreator.random().animal().name())
      .genitiveNeuter(MotherCreator.random().animal().name())
      .genitivePlural(MotherCreator.random().animal().name())
      .dativeMasculine(MotherCreator.random().animal().name())
      .dativeFeminine(MotherCreator.random().animal().name())
      .dativeNeuter(MotherCreator.random().animal().name())
      .dativePlural(MotherCreator.random().animal().name())
      .instrumentalMasculine(MotherCreator.random().animal().name())
      .instrumentalFeminine(MotherCreator.random().animal().name())
      .instrumentalNeuter(MotherCreator.random().animal().name())
      .instrumentalPlural(MotherCreator.random().animal().name())
      .prepositionalMasculine(MotherCreator.random().animal().name())
      .prepositionalFeminine(MotherCreator.random().animal().name())
      .prepositionalNeuter(MotherCreator.random().animal().name())
      .prepositionalPlural(MotherCreator.random().animal().name());
  }

}
