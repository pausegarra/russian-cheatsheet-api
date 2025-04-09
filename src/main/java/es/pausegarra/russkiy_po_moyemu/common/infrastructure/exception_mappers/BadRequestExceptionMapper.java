package es.pausegarra.russkiy_po_moyemu.common.infrastructure.exception_mappers;

import es.pausegarra.russkiy_po_moyemu.common.domain.exception.BadRequest;
import es.pausegarra.russkiy_po_moyemu.common.infrastructure.presentations.ApiExceptionPresentation;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class BadRequestExceptionMapper implements ExceptionMapper<BadRequest> {

  @Override
  public Response toResponse(BadRequest exception) {
    ApiExceptionPresentation presentation = ApiExceptionPresentation.create(
      exception.getMessage(),
      Response.Status.BAD_REQUEST.name(),
      Response.Status.BAD_REQUEST.getStatusCode()
    );

    return Response.status(Response.Status.BAD_REQUEST).entity(presentation).build();
  }

}
