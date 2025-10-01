package es.pausegarra.template.common.infrastructure.exception_mappers;

import es.pausegarra.template.common.domain.exception.Unauthenticated;
import es.pausegarra.template.common.infrastructure.presentations.ApiExceptionPresentation;
import io.quarkus.security.UnauthorizedException;
import jakarta.ws.rs.ext.Provider;
import org.jboss.resteasy.reactive.RestResponse;
import org.jboss.resteasy.reactive.server.ServerExceptionMapper;

@Provider
public class UnauthenticatedExceptionMapper {

  @ServerExceptionMapper
  public RestResponse<ApiExceptionPresentation> toResponse(UnauthorizedException e) {
    ApiExceptionPresentation presentation = createPresentation(e.getMessage());

    return RestResponse.status(RestResponse.Status.UNAUTHORIZED, presentation);
  }

  @ServerExceptionMapper
  public RestResponse<ApiExceptionPresentation> toResponse(Unauthenticated e) {
    ApiExceptionPresentation presentation = createPresentation(e.getMessage(), e.getCode());

    return RestResponse.status(RestResponse.Status.UNAUTHORIZED, presentation);
  }

  private ApiExceptionPresentation createPresentation(String message) {
    return ApiExceptionPresentation.create(
      message,
      RestResponse.Status.UNAUTHORIZED.name(),
      RestResponse.Status.UNAUTHORIZED.getStatusCode()
    );
  }

  private ApiExceptionPresentation createPresentation(String message, String code) {
    return ApiExceptionPresentation.create(
      message,
      code.toUpperCase(),
      RestResponse.Status.UNAUTHORIZED.getStatusCode()
    );
  }

}
