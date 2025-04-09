package es.pausegarra.russkiy_po_moyemu.common.infrastructure.exception_mappers;

import es.pausegarra.russkiy_po_moyemu.common.domain.exception.Forbidden;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ForbiddenExceptionMapper implements ExceptionMapper<Forbidden> {

  @Override
  public Response toResponse(Forbidden exception) {
    return Response.status(Response.Status.FORBIDDEN).build();
  }

}
