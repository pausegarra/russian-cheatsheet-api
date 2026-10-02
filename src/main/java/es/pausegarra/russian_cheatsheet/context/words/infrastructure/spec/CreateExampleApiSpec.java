package es.pausegarra.russian_cheatsheet.context.words.infrastructure.spec;

import es.pausegarra.russian_cheatsheet.context.words.application.dto.ExampleSentenceDto;
import es.pausegarra.russian_cheatsheet.context.words.application.use_cases.create_example.CreateExampleDto;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.resteasy.reactive.RestResponse;

@Path("/examples")
@Tag(name = "Examples")
public interface CreateExampleApiSpec {

  @POST
  @Operation(summary = "Create an example sentence")
  @APIResponse(responseCode = "201", description = "The example sentence was created")
  @APIResponse(responseCode = "400", description = "Invalid example data")
  @APIResponse(responseCode = "401", description = "The caller is not authenticated")
  @APIResponse(responseCode = "403", description = "The caller cannot create examples")
  @APIResponse(responseCode = "404", description = "A linked word does not exist")
  @SecurityRequirement(name = "SecurityScheme")
  RestResponse<ExampleSentenceDto> createExample(CreateExampleDto request);
}
