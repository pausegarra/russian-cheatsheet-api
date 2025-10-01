package es.pausegarra.template.common.infrastructure.exception_mappers;

import es.pausegarra.template.common.domain.exception.NotFound;
import es.pausegarra.template.common.infrastructure.presentations.ApiExceptionPresentation;
import jakarta.ws.rs.ext.Provider;
import org.jboss.resteasy.reactive.RestResponse;
import org.jboss.resteasy.reactive.server.ServerExceptionMapper;

@Provider
public class NotFoundExceptionMapper {

  @ServerExceptionMapper
  public RestResponse<ApiExceptionPresentation> toResponse(NotFound e) {
    ApiExceptionPresentation presentation = ApiExceptionPresentation.create(
      e.getMessage(),
      RestResponse.Status.NOT_FOUND.name(),
      RestResponse.Status.NOT_FOUND.getStatusCode()
    );

    return RestResponse.status(RestResponse.Status.NOT_FOUND, presentation);
  }

}
