package es.pausegarra.russian_cheatsheet.common.infrastructure.exception_mappers;

import es.pausegarra.russian_cheatsheet.common.domain.exception.Forbidden;
import es.pausegarra.russian_cheatsheet.common.infrastructure.presentations.ApiExceptionPresentation;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import org.jboss.resteasy.reactive.RestResponse;
import org.jboss.resteasy.reactive.server.ServerExceptionMapper;

@Provider
public class ForbiddenExceptionMapper {

  @ServerExceptionMapper
  public RestResponse<ApiExceptionPresentation> toResponse(Forbidden exception) {
    ApiExceptionPresentation presentation = ApiExceptionPresentation.create(
      exception.getMessage(),
      Response.Status.FORBIDDEN.name(),
      Response.Status.FORBIDDEN.getStatusCode()
    );

    return RestResponse.status(Response.Status.FORBIDDEN, presentation);
  }

}
