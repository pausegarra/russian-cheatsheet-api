package es.pausegarra.russian_cheatsheet.common.infrastructure.presentations;

import jakarta.ws.rs.core.Response.Status;
import lombok.AccessLevel;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Data
public class ValidationErrorPresentation {

  private final String message;

  private final Status status;

  private final String code;

  private final Set<ValidationError> errors;

  public static ValidationErrorPresentation create(String message, Status status, String code, Set<ValidationError> errors) {
    return new ValidationErrorPresentation(message, status, code, errors);
  }

}
