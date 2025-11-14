package es.pausegarra.russian_cheatsheet.context.words.domain.exception;

import es.pausegarra.russian_cheatsheet.common.domain.exception.NotFound;

public class WordNotFound extends NotFound {

  public WordNotFound(String id) {
    super("Word with id " + id + " not found");
  }

}
