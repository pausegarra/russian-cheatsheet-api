package es.pausegarra.russian_cheatsheet.vocabulary.infrastructure.spec;

import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.resteasy.reactive.RestResponse;

@Path("/words/{id}")
@Tag(name = "Words")
public interface DeleteWordApiSpec {

  @DELETE
  @Operation(summary = "Delete word")
  @APIResponse(
    responseCode = "204", description = "Word deleted"
  )
  @APIResponse(
    responseCode = "404", description = "Word not found"
  )
  @APIResponse(
    responseCode = "401", description = "Unauthenticated"
  )
  @APIResponse(
    responseCode = "403", description = "Forbidden"
  )
  @SecurityRequirement(name = "SecurityScheme")
  RestResponse<Void> deleteWord(
    @PathParam("id")
    @Parameter(
      name = "id", required = true
    )
    String id
  );

}
