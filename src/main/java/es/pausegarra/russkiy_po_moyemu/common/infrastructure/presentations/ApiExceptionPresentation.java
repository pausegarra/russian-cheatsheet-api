package es.pausegarra.russkiy_po_moyemu.common.infrastructure.presentations;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ApiExceptionPresentation {

  private final String message;

  private final String code;

  private final int status;

  public static ApiExceptionPresentation create(String message, String code, int status) {
    return new ApiExceptionPresentation(message, code, status);
  }

}
