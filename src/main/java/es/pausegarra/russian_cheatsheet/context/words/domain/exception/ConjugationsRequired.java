package es.pausegarra.russian_cheatsheet.context.words.domain.exception;

import es.pausegarra.russian_cheatsheet.common.domain.exception.BadRequest;

public class ConjugationsRequired extends BadRequest {

  public ConjugationsRequired() {
    super("Conjugations are required for word type VERB", "CONJUGATIONS_REQUIRED");
  }

}
