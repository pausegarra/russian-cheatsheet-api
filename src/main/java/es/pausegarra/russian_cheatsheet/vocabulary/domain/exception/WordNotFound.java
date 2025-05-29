package es.pausegarra.russian_cheatsheet.vocabulary.domain.exception;

import es.pausegarra.russian_cheatsheet.common.domain.exception.NotFound;

public final class WordNotFound extends NotFound {

  public WordNotFound(String id) {
    super("Word with ID " + id + " not found");
  }

}
