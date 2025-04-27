package es.pausegarra.russian_cheatsheet.vocabulary.infrastructure.spec;

import es.pausegarra.russian_cheatsheet.vocabulary.infrastructure.presentations.WordPresentation;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;
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
  @Operation(summary = "Find word by id")
  @APIResponse(
    responseCode = "200",
    description = "Word found"
  )
  @APIResponse(
    responseCode = "404",
    description = "Word not found"
  )
  public RestResponse<WordPresentation> findWordById(
    @PathParam("id")
    @Parameter(
      name = "id",
      in = ParameterIn.PATH,
      required = true
    )
    String id
  );

}
