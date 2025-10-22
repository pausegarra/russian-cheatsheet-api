package es.pausegarra.russian_cheatsheet.common.infrastructure.exception_mappers;

import es.pausegarra.russian_cheatsheet.common.domain.exception.Forbidden;
import es.pausegarra.russian_cheatsheet.common.infrastructure.presentations.ApiExceptionPresentation;
import io.quarkus.security.ForbiddenException;
import jakarta.ws.rs.ext.Provider;
import org.jboss.resteasy.reactive.RestResponse;
import org.jboss.resteasy.reactive.server.ServerExceptionMapper;

@Provider
public class ForbiddenExceptionMapper {

  @ServerExceptionMapper
  public RestResponse<ApiExceptionPresentation> toResponse(ForbiddenException e) {
    ApiExceptionPresentation presentation = createPresentation(e.getMessage());

    return RestResponse.status(RestResponse.Status.FORBIDDEN, presentation);
  }

  @ServerExceptionMapper
  public RestResponse<ApiExceptionPresentation> toResponse(Forbidden e) {
    ApiExceptionPresentation presentation = createPresentation(e.getMessage(), e.getCode());

    return RestResponse.status(RestResponse.Status.FORBIDDEN, presentation);
  }

  private ApiExceptionPresentation createPresentation(String message) {
    return ApiExceptionPresentation.create(
      message,
      RestResponse.Status.FORBIDDEN.name(),
      RestResponse.Status.FORBIDDEN.getStatusCode()
    );
  }

  private ApiExceptionPresentation createPresentation(String message, String code) {
    return ApiExceptionPresentation.create(message, code.toUpperCase(), RestResponse.Status.FORBIDDEN.getStatusCode());
  }

}
