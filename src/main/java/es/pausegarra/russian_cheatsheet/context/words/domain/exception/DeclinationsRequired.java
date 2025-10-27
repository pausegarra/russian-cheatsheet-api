package es.pausegarra.russian_cheatsheet.context.words.domain.exception;

import es.pausegarra.russian_cheatsheet.common.domain.exception.BadRequest;

public class DeclinationsRequired extends BadRequest {

  public DeclinationsRequired() {
    super("Declinations are required for word type NOUN", "DECLINATIONS_REQUIRED");
  }

}
