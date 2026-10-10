package es.pausegarra.russian_cheatsheet.context.words.infrastructure.spec;

import es.pausegarra.russian_cheatsheet.context.words.application.dto.ExampleSentenceDto;
import es.pausegarra.russian_cheatsheet.context.words.infrastructure.requests.UpdateExampleRequest;
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

@Path("/v1/examples/{id}")
@Tag(name = "Examples")
public interface UpdateExampleApiSpec {

  @PUT
  @Operation(summary = "Update an example sentence")
  @APIResponse(responseCode = "200", description = "The example sentence was updated")
  @APIResponse(responseCode = "400", description = "Invalid example data or UUID")
  @APIResponse(responseCode = "401", description = "The caller is not authenticated")
  @APIResponse(responseCode = "403", description = "The caller cannot update examples")
  @APIResponse(responseCode = "404", description = "The example or a linked word does not exist")
  @SecurityRequirement(name = "SecurityScheme")
  RestResponse<ExampleSentenceDto> updateExample(
    @PathParam("id")
    @Parameter(name = "id", in = ParameterIn.PATH, required = true, description = "The UUID of the example")
    String id,
    @RequestBody UpdateExampleRequest request
  );
}
