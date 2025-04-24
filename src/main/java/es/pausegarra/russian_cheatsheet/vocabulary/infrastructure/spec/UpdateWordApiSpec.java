package es.pausegarra.russian_cheatsheet.vocabulary.infrastructure.spec;

import es.pausegarra.russian_cheatsheet.vocabulary.infrastructure.requests.UpdateWordRequest;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.resteasy.reactive.RestResponse;

@Path("/words/{id}")
@Tag(name = "Words")
public interface UpdateWordApiSpec {

  @PATCH
  @Operation(summary = "Update word")
  @SecurityRequirement(name = "SecurityScheme")
  @APIResponse(
    responseCode = "204",
    description = "Word updated"
  )
  @APIResponse(
    responseCode = "400",
    description = "Bad request"
  )
  @APIResponse(
    responseCode = "401",
    description = "Unauthenticated"
  )
  @APIResponse(
    responseCode = "403",
    description = "Forbidden"
  )
  @APIResponse(
    responseCode = "404",
    description = "Not found"
  )
  RestResponse<Void> updateWord(
    @PathParam("id")
    @Parameter(
      name = "id",
      description = "Word ID",
      required = true
    )
    String id,

    @RequestBody(
      description = "Word to update",
      required = true,
      content = @Content(
        mediaType = "application/json",
        schema = @Schema(
          implementation = UpdateWordRequest.class
        )
      )
    )
    UpdateWordRequest request
  );

}
