package es.pausegarra.russian_cheatsheet.common.infrastructure.exception_mappers;

import es.pausegarra.russian_cheatsheet.common.infrastructure.presentations.ApiExceptionPresentation;
import io.quarkus.security.UnauthorizedException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.jboss.resteasy.reactive.RestResponse;

@Provider
public class UnauthenticatedExceptionMapper implements ExceptionMapper<UnauthorizedException> {

  @Override
  public Response toResponse(UnauthorizedException e) {
    ApiExceptionPresentation presentation = ApiExceptionPresentation.create(
      e.getMessage(),
      Response.Status.UNAUTHORIZED.name(),
      Response.Status.UNAUTHORIZED.getStatusCode()
    );

    return RestResponse.ResponseBuilder.create(Response.Status.UNAUTHORIZED, presentation)
      .header("Content-Type", "application/json")
      .build()
      .toResponse();
  }

}
