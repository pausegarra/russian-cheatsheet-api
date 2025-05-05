package es.pausegarra.russian_cheatsheet.auth.infrastructure.spec;

import es.pausegarra.russian_cheatsheet.auth.application.dto.PermissionsDto;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.resteasy.reactive.RestResponse;

@Path("/auth/profile/permissions")
@Tag(name = "Auth")
public interface FindAuthRolesApiSpec {

  @GET
  @Operation(summary = "Get the roles and permissions for current user")
  @APIResponse(
    description = "Permissions found",
    responseCode = "200"
  )
  @APIResponse(
    description = "Unauthenticated",
    responseCode = "401"
  )
  @APIResponse(
    description = "Server error",
    responseCode = "500"
  )
  @SecurityRequirement(
    name = "SecurityScheme"
  )
  RestResponse<PermissionsDto> getPermissionsAndRoles();

}
