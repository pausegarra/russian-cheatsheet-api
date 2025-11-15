package es.pausegarra.russian_cheatsheet.context.words.infrastructure.spec;

import es.pausegarra.russian_cheatsheet.context.words.application.dto.WordDto;
import es.pausegarra.russian_cheatsheet.context.words.infrastructure.requests.UpdateWordRequest;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.ParameterIn;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.resteasy.reactive.RestResponse;

@Path("/words/{wordId}")
@Tag(name = "Words")
public interface UpdateWordApiSpec {

  @PUT
  @Operation(summary = "Update a word")
  @APIResponse(responseCode = "200", description = "The word has been updated")
  @APIResponse(responseCode = "404", description = "The word does not exist")
  @APIResponse(responseCode = "401", description = "The user is not authenticated")
  @APIResponse(responseCode = "403", description = "The user is not authorized to update the word")
  @APIResponse(responseCode = "500", description = "An unexpected error occurred")
  @SecurityRequirement(name = "SecurityScheme")
  RestResponse<WordDto> updateWord(
    @PathParam("wordId")
    @Parameter(name = "wordId", in = ParameterIn.PATH, required = true, description = "The id of the word to update")
    String wordId,

    @RequestBody
    UpdateWordRequest request
  );

}
