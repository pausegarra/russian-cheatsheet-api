package es.pausegarra.russian_cheatsheet.vocabulary.infrastructure.spec;

import es.pausegarra.russian_cheatsheet.common.infrastructure.presentations.SimplePresentation;
import es.pausegarra.russian_cheatsheet.vocabulary.infrastructure.requests.CreateWordRequest;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.resteasy.reactive.RestResponse;

@Path("/words")
@Tag(name = "Words")
public interface CreateWordApiSpec {

  @POST
  @Operation(summary = "Create word")
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
    responseCode = "201", description = "Word created"
  )
  @SecurityRequirement(name = "SecurityScheme")
  RestResponse<SimplePresentation> createWord(
    @RequestBody(
      description = "Word to create", required = true, content = @Content(
      mediaType = "application/json", schema = @Schema(
      implementation = CreateWordRequest.class
    )
    )
    )
    CreateWordRequest request
  );

}
