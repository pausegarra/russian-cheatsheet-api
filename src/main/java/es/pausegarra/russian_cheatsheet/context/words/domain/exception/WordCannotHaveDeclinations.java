package es.pausegarra.russian_cheatsheet.context.words.domain.exception;

import es.pausegarra.russian_cheatsheet.common.domain.exception.BadRequest;

public class WordCannotHaveDeclinations extends BadRequest {

  public WordCannotHaveDeclinations(String word) {
    super("Trying to add declinations to the word " + word + " that is not a noun");
  }

}
