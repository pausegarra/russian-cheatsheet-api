package es.pausegarra.russian_cheatsheet.context.words.domain.exception;

import es.pausegarra.russian_cheatsheet.common.domain.exception.NotFound;

public class ExampleNotFound extends NotFound {

  public ExampleNotFound(String id) {
    super("Example with id " + id + " not found");
  }
}
