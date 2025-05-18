package es.pausegarra.russian_cheatsheet.mother.words.entities;

import es.pausegarra.russian_cheatsheet.mother.MotherCreator;
import es.pausegarra.russian_cheatsheet.vocabulary.domain.entities.WordCasesEntity;

public class WordCaseMother {

  public static WordCasesEntity.WordCasesEntityBuilder random() {
    return WordCasesEntity.builder()
      .nominative(MotherCreator.random()
        .name()
        .toString())
      .genitive(MotherCreator.random()
        .name()
        .toString())
      .dative(MotherCreator.random()
        .name()
        .toString())
      .accusative(MotherCreator.random()
        .name()
        .toString())
      .instrumental(MotherCreator.random()
        .name()
        .toString())
      .prepositional(MotherCreator.random()
        .name()
        .toString());
  }

}
