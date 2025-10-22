package es.pausegarra.russian_cheatsheet.common.domain.exception;

import lombok.Getter;

@Getter
public class Forbidden extends RuntimeException {

  private String code = "FORBIDDEN";

  public Forbidden(String message) {
    super(message);
  }

  public Forbidden(String message, String code) {
    super(message);
    this.code = code;
  }

}
