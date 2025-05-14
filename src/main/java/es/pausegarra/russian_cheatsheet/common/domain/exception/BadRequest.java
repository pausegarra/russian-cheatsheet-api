package es.pausegarra.russian_cheatsheet.common.domain.exception;

import lombok.Getter;

@Getter
public class BadRequest extends RuntimeException {

  private String code = "BAD_REQUEST";

  public BadRequest(String message) {
    super(message);
  }

  public BadRequest(String message, String code) {
    super(message);
    this.code = code;
  }

}
