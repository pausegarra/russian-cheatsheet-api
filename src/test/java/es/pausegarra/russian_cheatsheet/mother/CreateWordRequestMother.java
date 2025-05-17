package es.pausegarra.russian_cheatsheet.mother;

import es.pausegarra.russian_cheatsheet.vocabulary.domain.enums.WordTypes;
import es.pausegarra.russian_cheatsheet.vocabulary.infrastructure.requests.CreateWordRequest;

public class CreateWordRequestMother {

  public static CreateWordRequest.CreateWordRequestBuilder random() {
    return CreateWordRequest.builder()
      .russian(MotherCreator.random()
        .name()
        .toString())
      .english(MotherCreator.random()
        .name()
        .toString())
      .spanish(MotherCreator.random()
        .name()
        .toString())
      .type(MotherCreator.random()
        .options()
        .option(WordTypes.class)
        .toString())
      .conjugations(null);
  }

}
