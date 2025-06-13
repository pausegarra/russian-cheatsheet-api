package es.pausegarra.template.common.infrastructure.exception_mappers;

import es.pausegarra.template.common.domain.exception.BadRequest;
import es.pausegarra.template.common.infrastructure.presentations.ApiExceptionPresentation;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import org.jboss.resteasy.reactive.RestResponse;
import org.jboss.resteasy.reactive.server.ServerExceptionMapper;

@Provider
public class BadRequestExceptionMapper {

  @ServerExceptionMapper
  public RestResponse<ApiExceptionPresentation> toResponse(BadRequest exception) {
    ApiExceptionPresentation presentation = ApiExceptionPresentation.create(
      exception.getMessage(),
      exception.getCode(),
      Response.Status.BAD_REQUEST.getStatusCode()
    );

    return RestResponse.status(Response.Status.BAD_REQUEST, presentation);
  }

}
