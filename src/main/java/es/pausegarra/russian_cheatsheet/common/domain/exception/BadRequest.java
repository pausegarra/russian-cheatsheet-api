package es.pausegarra.russian_cheatsheet.common.domain.exception;

public class BadRequest extends RuntimeException {

  public BadRequest(String message) {
    super(message);
  }

}
