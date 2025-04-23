package es.pausegarra.russian_cheatsheet.common.infrastructure.exception_mappers;

import es.pausegarra.russian_cheatsheet.common.domain.exception.Forbidden;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ForbiddenExceptionMapper implements ExceptionMapper<Forbidden> {

  @Override
  public Response toResponse(Forbidden exception) {
    return Response.status(Response.Status.FORBIDDEN)
      .build();
  }

}
