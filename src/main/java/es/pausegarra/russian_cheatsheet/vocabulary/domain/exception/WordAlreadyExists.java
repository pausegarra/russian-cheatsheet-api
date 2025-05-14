package es.pausegarra.russian_cheatsheet.vocabulary.domain.exception;

import es.pausegarra.russian_cheatsheet.common.domain.exception.BadRequest;

public class WordAlreadyExists extends BadRequest {
  public WordAlreadyExists(String word) {
    super("Word already exists: " + word, "WORD_ALREADY_EXISTS");
  }
}
