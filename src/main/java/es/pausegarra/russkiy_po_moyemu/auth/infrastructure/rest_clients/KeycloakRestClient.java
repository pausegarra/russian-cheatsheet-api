package es.pausegarra.russkiy_po_moyemu.auth.infrastructure.rest_clients;

import es.pausegarra.russkiy_po_moyemu.auth.infrastructure.dto.PermissionDto;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.List;

@Path("/protocol/openid-connect/token")
@RegisterRestClient(configKey = "keycloak")
@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
@Produces(MediaType.APPLICATION_JSON)
public interface KeycloakRestClient {

  @POST
  List<PermissionDto> getEntitlement(
      @FormParam("grant_type") String grantType,
      @FormParam("audience") String audience,
      @FormParam("response_mode") String responseMode,
      @HeaderParam("Authorization") String authorization
  );

}
