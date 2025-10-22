package es.pausegarra.russian_cheatsheet.common.domain.vo.exception;

import es.pausegarra.russian_cheatsheet.common.domain.exception.BadRequest;

public class InvalidSlugException extends BadRequest {

  public InvalidSlugException(String slug) {
    super("The slug " + slug + " is not valid");
  }

  public InvalidSlugException(String slug, String message) {
    super("The slug " + slug + " is not valid: " + message);
  }

}
