package es.pausegarra.russian_cheatsheet.common.infrastructure.exception_mappers;

import es.pausegarra.russian_cheatsheet.common.infrastructure.presentations.ApiExceptionPresentation;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class IllegalArgumentExceptionMapper implements ExceptionMapper<IllegalArgumentException> {

  @Override
  public Response toResponse(IllegalArgumentException e) {
    ApiExceptionPresentation presentation = ApiExceptionPresentation.create(
      e.getMessage(),
      Response.Status.BAD_REQUEST.name(),
      Response.Status.BAD_REQUEST.getStatusCode()
    );

    return Response.status(Response.Status.BAD_REQUEST)
      .entity(presentation)
      .build();
  }

}
