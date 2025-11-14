package es.pausegarra.russian_cheatsheet.context.words.infrastructure.spec;

import es.pausegarra.russian_cheatsheet.context.words.application.dto.WordDto;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.ParameterIn;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.resteasy.reactive.RestResponse;

@Path("/words/{id}")
@Tag(name = "Words")
public interface FindWordByIdApiSpec {

  @GET
  @Operation(summary = "Find Word By Id")
  @APIResponse(responseCode = "200", description = "OK")
  @APIResponse(responseCode = "400", description = "Bad Request")
  @APIResponse(responseCode = "404", description = "Not Found")
  @APIResponse(responseCode = "500", description = "Internal Server Error")
  RestResponse<WordDto> findWordById(
    @PathParam("id")
    @Parameter(name = "id", description = "The id of the word to find", required = true, in = ParameterIn.PATH)
    String id
  );

}
