package es.pausegarra.russian_cheatsheet.common.infrastructure.exception_mappers;

import es.pausegarra.russian_cheatsheet.common.domain.exception.NotFound;
import es.pausegarra.russian_cheatsheet.common.infrastructure.presentations.ApiExceptionPresentation;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class NotFoundExceptionMapper implements ExceptionMapper<NotFound> {

  @Override
  public Response toResponse(NotFound exception) {
    ApiExceptionPresentation presentation = ApiExceptionPresentation.create(
      exception.getMessage(),
      Response.Status.BAD_REQUEST.name(),
      Response.Status.NOT_FOUND.getStatusCode()
    );

    return Response.status(Response.Status.NOT_FOUND)
      .entity(presentation)
      .build();
  }

}
