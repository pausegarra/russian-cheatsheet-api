package es.pausegarra.russian_cheatsheet.context.words.infrastructure.spec;

import es.pausegarra.russian_cheatsheet.context.words.application.dto.WordRelationBatchResultDto;
import es.pausegarra.russian_cheatsheet.context.words.infrastructure.requests.WordRelationBatchRequest;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.resteasy.reactive.RestResponse;

import java.util.List;

@Path("/v1/words/relations/batch")
@Tag(name = "Word Relations")
public interface WordRelationsBatchApiSpec {

  @POST
  @Operation(summary = "Create outgoing relations in a batch")
  @APIResponse(responseCode = "200", description = "All relations already existed")
  @APIResponse(responseCode = "201", description = "At least one relation was created")
  @APIResponse(responseCode = "400", description = "Invalid or oversized batch")
  @APIResponse(responseCode = "401", description = "The caller is not authenticated")
  @APIResponse(responseCode = "403", description = "The caller cannot create relations")
  @APIResponse(responseCode = "404", description = "A source or target word does not exist")
  @SecurityRequirement(name = "SecurityScheme")
  RestResponse<List<WordRelationBatchResultDto>> createRelations(
    @RequestBody List<WordRelationBatchRequest> requests
  );

  @DELETE
  @Operation(summary = "Delete outgoing relations in a batch")
  @APIResponse(responseCode = "204", description = "All requested directed relations were deleted")
  @APIResponse(responseCode = "400", description = "Invalid or oversized batch")
  @APIResponse(responseCode = "401", description = "The caller is not authenticated")
  @APIResponse(responseCode = "403", description = "The caller cannot delete relations")
  @APIResponse(responseCode = "404", description = "A source, target, or exact relation does not exist")
  @SecurityRequirement(name = "SecurityScheme")
  RestResponse<Void> deleteRelations(
    @RequestBody List<WordRelationBatchRequest> requests
  );

}
