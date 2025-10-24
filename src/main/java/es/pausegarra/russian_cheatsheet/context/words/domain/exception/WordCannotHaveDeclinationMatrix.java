package es.pausegarra.russian_cheatsheet.context.words.domain.exception;

import es.pausegarra.russian_cheatsheet.common.domain.exception.BadRequest;

public class WordCannotHaveDeclinationMatrix extends BadRequest {

  public WordCannotHaveDeclinationMatrix(String word) {
    super("Trying to add declination matrix to the word " + word + " that is not an adjective, pronoun, participle or ordinal");
  }

}
