package es.pausegarra.russkiy_po_moyemu.common.infrastructure.presentations;

public record ApiExceptionPresentation(
  String message, String code, int status
) {

  public static ApiExceptionPresentation create(String message, String code, int status) {
    return new ApiExceptionPresentation(message, code, status);
  }

}
