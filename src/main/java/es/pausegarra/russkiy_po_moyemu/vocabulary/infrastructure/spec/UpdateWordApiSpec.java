package es.pausegarra.russkiy_po_moyemu.vocabulary.infrastructure.spec;

import es.pausegarra.russkiy_po_moyemu.vocabulary.infrastructure.requests.UpdateWordRequest;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Path("/words/{id}")
@Tag(name = "Words")
public interface UpdateWordApiSpec {

  @PATCH
  @Operation(summary = "Update word")
  @SecurityRequirement(name = "SecurityScheme")
  @APIResponse(
    responseCode = "204", description = "Word updated"
  )
  @APIResponse(responseCode = "400", description = "Bad request")
  @APIResponse(responseCode = "401", description = "Unauthenticated")
  @APIResponse(responseCode = "403", description = "Forbidden")
  @APIResponse(responseCode = "404", description = "Not found")
  Response updateWord(String id, UpdateWordRequest request);

}
