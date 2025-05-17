package es.pausegarra.russian_cheatsheet.mother;

import es.pausegarra.russian_cheatsheet.vocabulary.domain.enums.WordTypes;
import es.pausegarra.russian_cheatsheet.vocabulary.infrastructure.requests.UpdateWordRequest;

public class UpdateWordRequestMother {

  public static UpdateWordRequest.UpdateWordRequestBuilder random() {
    return UpdateWordRequest.builder()
      .russian(MotherCreator.random().name().toString())
      .english(MotherCreator.random().name().toString())
      .spanish(MotherCreator.random().name().toString())
      .type(MotherCreator.random().options().option(WordTypes.class).toString())
      .conjugations(null);
  }

}
