package es.pausegarra.russian_cheatsheet.context.words.domain.exception;

import es.pausegarra.russian_cheatsheet.common.domain.exception.BadRequest;

public class DeclinationsRequired extends BadRequest {

  public DeclinationsRequired() {
    super(
      "Declinations are required for word types NOUN, PRONOUN_NOUN, NUMERAL_CARDINAL or for declination-matrix types ADJECTIVE, SHORT_ADJECTIVE, PARTICIPLE, ORDINAL, PRONOUN_ADJECTIVE, NUMERAL_ADJECTIVE",
      "DECLINATIONS_REQUIRED"
    );
  }

}
