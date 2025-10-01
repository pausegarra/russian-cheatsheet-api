package es.pausegarra.template.common.infrastructure.exception_mappers;

import es.pausegarra.template.common.domain.exception.BadRequest;
import es.pausegarra.template.common.infrastructure.presentations.ApiExceptionPresentation;
import jakarta.ws.rs.ext.Provider;
import org.jboss.resteasy.reactive.RestResponse;
import org.jboss.resteasy.reactive.server.ServerExceptionMapper;

@Provider
public class BadRequestExceptionMapper {

  @ServerExceptionMapper
  public RestResponse<ApiExceptionPresentation> toResponse(BadRequest e) {
    ApiExceptionPresentation presentation = ApiExceptionPresentation.create(
      e.getMessage(),
      e.getCode().toUpperCase(),
      RestResponse.Status.BAD_REQUEST.getStatusCode()
    );

    return RestResponse.status(RestResponse.Status.BAD_REQUEST, presentation);
  }

}
