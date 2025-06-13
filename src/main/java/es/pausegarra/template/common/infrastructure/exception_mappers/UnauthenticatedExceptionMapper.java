package es.pausegarra.template.common.infrastructure.exception_mappers;

import es.pausegarra.template.common.infrastructure.presentations.ApiExceptionPresentation;
import io.quarkus.security.UnauthorizedException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import org.jboss.resteasy.reactive.RestResponse;
import org.jboss.resteasy.reactive.server.ServerExceptionMapper;

@Provider
public class UnauthenticatedExceptionMapper {

  @ServerExceptionMapper
  public RestResponse<ApiExceptionPresentation> toResponse(UnauthorizedException e) {
    ApiExceptionPresentation presentation = ApiExceptionPresentation.create(
      e.getMessage(),
      Response.Status.UNAUTHORIZED.name(),
      Response.Status.UNAUTHORIZED.getStatusCode()
    );

    return RestResponse.status(Response.Status.UNAUTHORIZED, presentation);
  }

}
