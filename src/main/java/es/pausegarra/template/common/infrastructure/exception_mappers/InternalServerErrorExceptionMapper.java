package es.pausegarra.template.common.infrastructure.exception_mappers;

import es.pausegarra.template.common.domain.exception.InternalServerError;
import es.pausegarra.template.common.infrastructure.presentations.ApiExceptionPresentation;
import jakarta.ws.rs.ext.Provider;
import org.jboss.resteasy.reactive.RestResponse;
import org.jboss.resteasy.reactive.server.ServerExceptionMapper;

@Provider
public class InternalServerErrorExceptionMapper {

  @ServerExceptionMapper
  public RestResponse<ApiExceptionPresentation> toResponse(InternalServerError e) {
    ApiExceptionPresentation presentation = ApiExceptionPresentation.create(
      e.getMessage(),
      RestResponse.Status.INTERNAL_SERVER_ERROR.name(),
      RestResponse.Status.INTERNAL_SERVER_ERROR.getStatusCode()
    );

    return RestResponse.status(RestResponse.Status.INTERNAL_SERVER_ERROR, presentation);
  }

}
