package es.pausegarra.template.common.domain.exception;

import lombok.Getter;

@Getter
public class Unauthenticated extends RuntimeException {

  private String code = "UNAUTHENTICATED";

  public Unauthenticated(String message) {
    super(message);
  }

  public Unauthenticated(String message, String code) {
    super(message);
    this.code = code;
  }

}
