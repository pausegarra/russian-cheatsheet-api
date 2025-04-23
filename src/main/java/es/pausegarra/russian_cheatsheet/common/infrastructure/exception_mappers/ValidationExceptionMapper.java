package es.pausegarra.russian_cheatsheet.common.infrastructure.exception_mappers;

import es.pausegarra.russian_cheatsheet.common.infrastructure.presentations.ValidationError;
import es.pausegarra.russian_cheatsheet.common.infrastructure.presentations.ValidationErrorPresentation;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.Set;
import java.util.stream.Collectors;

@Provider
public class ValidationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {

  @Override
  public Response toResponse(ConstraintViolationException exception) {
    Set<ValidationError> errors = exception.getConstraintViolations()
      .stream()
      .map(ValidationError::fromConstraint)
      .collect(Collectors.toSet());

    ValidationErrorPresentation presentation = ValidationErrorPresentation.create(
      "Unprocessable Entity",
      Response.Status.BAD_REQUEST, errors
    );

    return Response.status(Response.Status.BAD_REQUEST)
      .entity(presentation)
      .build();
  }

}
