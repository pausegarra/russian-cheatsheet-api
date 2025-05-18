package es.pausegarra.russian_cheatsheet.mother.words.requests;

import es.pausegarra.russian_cheatsheet.mother.MotherCreator;
import es.pausegarra.russian_cheatsheet.vocabulary.infrastructure.requests.WordCasesRequest;

public class WordCasesRequestMother {

  public static WordCasesRequest.WordCasesRequestBuilder random() {
    return WordCasesRequest.builder()
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
