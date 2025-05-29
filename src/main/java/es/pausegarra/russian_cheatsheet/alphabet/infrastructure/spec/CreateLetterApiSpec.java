package es.pausegarra.russian_cheatsheet.alphabet.infrastructure.spec;

import es.pausegarra.russian_cheatsheet.alphabet.infrastructure.requests.CreateLetterRequest;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.resteasy.reactive.RestResponse;

@Path("/letters")
@Tag(name = "Letters")
public interface CreateLetterApiSpec {

  @POST
  @Operation(summary = "Create letter")
  @APIResponse(
    responseCode = "400", description = "Bad request"
  )
  @APIResponse(
    responseCode = "401", description = "Unauthenticated"
  )
  @APIResponse(
    responseCode = "403", description = "Forbidden"
  )
  @APIResponse(
    responseCode = "201", description = "Letter created"
  )
  @SecurityRequirement(name = "SecurityScheme")
  RestResponse<String> createLetter(CreateLetterRequest request);

}
