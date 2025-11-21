package es.pausegarra.russian_cheatsheet.mother;

import es.pausegarra.russian_cheatsheet.context.words.domain.entities.WordDeclinationEntity;

import java.util.UUID;

public class WordDeclinationMother {

  public static WordDeclinationEntity.WordDeclinationEntityBuilder random() {
    return WordDeclinationEntity.builder()
      .id(UUID.randomUUID())
      .word(WordMother.random().build())
      .nominative(MotherCreator.random().animal().name())
      .accusative(MotherCreator.random().animal().name())
      .genitive(MotherCreator.random().animal().name())
      .dative(MotherCreator.random().animal().name())
      .instrumental(MotherCreator.random().animal().name())
      .prepositional(MotherCreator.random().animal().name())
      .nominativePlural(MotherCreator.random().animal().name())
      .accusativePlural(MotherCreator.random().animal().name())
      .genitivePlural(MotherCreator.random().animal().name())
      .dativePlural(MotherCreator.random().animal().name())
      .instrumentalPlural(MotherCreator.random().animal().name())
      .prepositionalPlural(MotherCreator.random().animal().name());
  }

}
