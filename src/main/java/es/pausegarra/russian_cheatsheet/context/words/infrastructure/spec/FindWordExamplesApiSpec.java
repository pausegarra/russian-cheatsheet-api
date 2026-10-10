package es.pausegarra.russian_cheatsheet.context.words.infrastructure.spec;

import es.pausegarra.russian_cheatsheet.common.application.pagination.PaginatedDto;
import es.pausegarra.russian_cheatsheet.context.words.application.dto.ExampleSentenceDto;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.resteasy.reactive.RestResponse;

@Path("/v1/words/{wordId}/examples")
@Tag(name = "Examples")
public interface FindWordExamplesApiSpec {

  @GET
  @Operation(summary = "Find examples for a word")
  @APIResponse(responseCode = "200", description = "The requested page of examples")
  @APIResponse(responseCode = "400", description = "Invalid word ID or pagination")
  @APIResponse(responseCode = "404", description = "The word does not exist")
  RestResponse<PaginatedDto<ExampleSentenceDto>> findWordExamples(
    @PathParam("wordId") String wordId,
    @QueryParam("page") @DefaultValue("0") int page,
    @QueryParam("perPage") @DefaultValue("25") int perPage
  );

}
