package es.pausegarra.russian_cheatsheet.context.words.infrastructure.spec;

import es.pausegarra.russian_cheatsheet.context.words.application.dto.WordDto;
import es.pausegarra.russian_cheatsheet.context.words.application.use_cases.create_word.CreateWordDto;
import es.pausegarra.russian_cheatsheet.context.words.infrastructure.requests.UpdateWordBatchRequest;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PUT;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.resteasy.reactive.RestResponse;

import java.util.List;

@Path("/words/batch")
@Tag(name = "Words")
public interface WordsBatchApiSpec {

  @POST
  @Operation(summary = "Create words in a batch")
  @APIResponse(responseCode = "201", description = "Words were created")
  @APIResponse(responseCode = "400", description = "Invalid or oversized batch")
  @APIResponse(responseCode = "401", description = "The caller is not authenticated")
  @APIResponse(responseCode = "403", description = "The caller cannot create words")
  @APIResponse(responseCode = "500", description = "An unexpected error occurred")
  @SecurityRequirement(name = "SecurityScheme")
  RestResponse<List<WordDto>> createWords(@RequestBody List<CreateWordDto> requests);

  @PUT
  @Operation(summary = "Update words in a batch")
  @APIResponse(responseCode = "200", description = "Words were updated")
  @APIResponse(responseCode = "400", description = "Invalid or oversized batch")
  @APIResponse(responseCode = "401", description = "The caller is not authenticated")
  @APIResponse(responseCode = "403", description = "The caller cannot update words")
  @APIResponse(responseCode = "404", description = "A word does not exist")
  @APIResponse(responseCode = "500", description = "An unexpected error occurred")
  @SecurityRequirement(name = "SecurityScheme")
  RestResponse<List<WordDto>> updateWords(@RequestBody List<UpdateWordBatchRequest> requests);
}
