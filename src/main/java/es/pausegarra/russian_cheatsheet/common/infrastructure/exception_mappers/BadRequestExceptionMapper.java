package es.pausegarra.russian_cheatsheet.common.infrastructure.exception_mappers;

import es.pausegarra.russian_cheatsheet.common.domain.exception.BadRequest;
import es.pausegarra.russian_cheatsheet.common.infrastructure.presentations.ApiExceptionPresentation;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.jboss.resteasy.reactive.RestResponse;

@Provider
public class BadRequestExceptionMapper implements ExceptionMapper<BadRequest> {

  @Override
  public Response toResponse(BadRequest exception) {
    ApiExceptionPresentation presentation = ApiExceptionPresentation.create(
      exception.getMessage(),
      Response.Status.BAD_REQUEST.name(),
      Response.Status.BAD_REQUEST.getStatusCode()
    );

    return RestResponse.ResponseBuilder.create(Response.Status.BAD_REQUEST, presentation)
      .header("Content-Type", "application/json")
      .build()
      .toResponse();
  }

}
