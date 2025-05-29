package es.pausegarra.russian_cheatsheet.common.infrastructure.exception_mappers;

import es.pausegarra.russian_cheatsheet.common.infrastructure.presentations.ApiExceptionPresentation;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import org.jboss.resteasy.reactive.RestResponse;
import org.jboss.resteasy.reactive.server.ServerExceptionMapper;

@Provider
public class IllegalArgumentExceptionMapper {

  @ServerExceptionMapper
  public RestResponse<ApiExceptionPresentation> toResponse(IllegalArgumentException e) {
    ApiExceptionPresentation presentation = ApiExceptionPresentation.create(
      e.getMessage(),
      "ILLEGAL_ARGUMENT",
      Response.Status.BAD_REQUEST.getStatusCode()
    );

    return RestResponse.ResponseBuilder.create(RestResponse.Status.BAD_REQUEST, presentation)
      .header("Content-Type", "application/json")
      .build();
  }

}
