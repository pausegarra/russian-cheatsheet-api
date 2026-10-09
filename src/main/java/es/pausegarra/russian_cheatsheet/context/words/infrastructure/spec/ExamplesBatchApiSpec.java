package es.pausegarra.russian_cheatsheet.context.words.infrastructure.spec;

import es.pausegarra.russian_cheatsheet.context.words.application.dto.ExampleSentenceDto;
import es.pausegarra.russian_cheatsheet.context.words.application.use_cases.create_example.CreateExampleDto;
import es.pausegarra.russian_cheatsheet.context.words.infrastructure.requests.UpdateExampleBatchRequest;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.DELETE;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.resteasy.reactive.RestResponse;

import java.util.List;
import java.util.UUID;

@Path("/api/v1/examples/batch")
@Tag(name = "Examples")
public interface ExamplesBatchApiSpec {

  @POST
  @Operation(summary = "Create examples in a batch")
  @APIResponse(responseCode = "201", description = "Examples were created")
  @APIResponse(responseCode = "400", description = "Invalid or oversized batch")
  @APIResponse(responseCode = "401", description = "The caller is not authenticated")
  @APIResponse(responseCode = "403", description = "The caller cannot create examples")
  @APIResponse(responseCode = "404", description = "A linked word does not exist")
  @APIResponse(responseCode = "500", description = "An unexpected error occurred")
  @SecurityRequirement(name = "SecurityScheme")
  RestResponse<List<ExampleSentenceDto>> createExamples(@RequestBody List<CreateExampleDto> requests);

  @PUT
  @Operation(summary = "Update examples in a batch")
  @APIResponse(responseCode = "200", description = "Examples were updated")
  @APIResponse(responseCode = "400", description = "Invalid or oversized batch")
  @APIResponse(responseCode = "401", description = "The caller is not authenticated")
  @APIResponse(responseCode = "403", description = "The caller cannot update examples")
  @APIResponse(responseCode = "404", description = "An example or linked word does not exist")
  @APIResponse(responseCode = "500", description = "An unexpected error occurred")
  @SecurityRequirement(name = "SecurityScheme")
  RestResponse<List<ExampleSentenceDto>> updateExamples(@RequestBody List<UpdateExampleBatchRequest> requests);

  @DELETE
  @Operation(summary = "Delete examples in a batch")
  @APIResponse(responseCode = "204", description = "All requested examples were deleted or already absent")
  @APIResponse(responseCode = "400", description = "Invalid or oversized batch")
  @APIResponse(responseCode = "401", description = "The caller is not authenticated")
  @APIResponse(responseCode = "403", description = "The caller cannot delete examples")
  @SecurityRequirement(name = "SecurityScheme")
  RestResponse<Void> deleteExamples(@RequestBody List<UUID> ids);
}
