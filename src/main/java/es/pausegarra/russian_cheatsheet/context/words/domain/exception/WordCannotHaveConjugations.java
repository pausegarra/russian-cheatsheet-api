package es.pausegarra.russian_cheatsheet.context.words.domain.exception;

import es.pausegarra.russian_cheatsheet.common.domain.exception.BadRequest;

public class WordCannotHaveConjugations extends BadRequest {

  public WordCannotHaveConjugations(String word) {
    super("Trying to add conjugations to the word " + word + " that is not a verb");
  }

}
