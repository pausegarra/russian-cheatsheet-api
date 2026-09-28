package es.pausegarra.russian_cheatsheet.context.words.infrastructure.spec;

import es.pausegarra.russian_cheatsheet.context.words.application.dto.ExampleSentenceDto;
import es.pausegarra.russian_cheatsheet.context.words.application.use_cases.import_example.ImportExampleRequestDto;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.resteasy.reactive.RestResponse;

@Path("/examples/import/{externalId}")
@Tag(name = "Examples")
public interface ImportExampleApiSpec {

  @PUT
  @Operation(summary = "Create or update an imported example sentence")
  @APIResponse(responseCode = "200", description = "The example sentence was saved")
  @APIResponse(responseCode = "400", description = "Invalid example data")
  @APIResponse(responseCode = "401", description = "The caller is not authenticated")
  @APIResponse(responseCode = "403", description = "The caller cannot import examples")
  @APIResponse(responseCode = "404", description = "A linked word does not exist")
  @SecurityRequirement(name = "SecurityScheme")
  RestResponse<ExampleSentenceDto> importExample(
    @PathParam("externalId") String externalId,
    @RequestBody ImportExampleRequestDto request
  );

}
