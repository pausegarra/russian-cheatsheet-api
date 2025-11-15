package es.pausegarra.russian_cheatsheet.context.words.infrastructure.spec;

import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.ParameterIn;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.resteasy.reactive.RestResponse;

@Path("/words/{wordId}/publish")
@Tag(name = "Words")
public interface PublishWordApiSpec {

  @PATCH
  @Operation(summary = "Publish a word")
  @APIResponse(responseCode = "204", description = "The word has been published")
  @APIResponse(responseCode = "404", description = "The word does not exist")
  @APIResponse(responseCode = "401", description = "The user is not authenticated")
  @APIResponse(responseCode = "403", description = "The user is not authorized to publish the word")
  @APIResponse(responseCode = "500", description = "An unexpected error occurred")
  @SecurityRequirement(name = "SecurityScheme")
  RestResponse<Void> publishWord(
    @PathParam("wordId")
    @Parameter(name = "wordId", in = ParameterIn.PATH, required = true, description = "The id of the word to publish")
    String wordId
  );

}
