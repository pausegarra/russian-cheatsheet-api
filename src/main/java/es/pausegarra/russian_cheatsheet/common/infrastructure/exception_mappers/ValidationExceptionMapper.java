package es.pausegarra.russian_cheatsheet.common.infrastructure.exception_mappers;

import es.pausegarra.russian_cheatsheet.common.infrastructure.presentations.ValidationError;
import es.pausegarra.russian_cheatsheet.common.infrastructure.presentations.ValidationErrorPresentation;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import org.jboss.resteasy.reactive.RestResponse;
import org.jboss.resteasy.reactive.server.ServerExceptionMapper;

import java.util.Set;
import java.util.stream.Collectors;

@Provider
public class ValidationExceptionMapper {

  @ServerExceptionMapper
  public RestResponse<ValidationErrorPresentation> toResponse(ConstraintViolationException exception) {
    Set<ValidationError> errors = exception.getConstraintViolations()
      .stream()
      .map(ValidationError::fromConstraint)
      .collect(Collectors.toSet());

    ValidationErrorPresentation presentation = ValidationErrorPresentation.create(
      "Unprocessable " + "Entity",
      Response.Status.BAD_REQUEST,
      "VALIDATION_ERROR",
      errors
    );

    return RestResponse.ResponseBuilder.create(RestResponse.Status.BAD_REQUEST, presentation)
      .header("Content-Type", "application/json")
      .build();
  }

}
