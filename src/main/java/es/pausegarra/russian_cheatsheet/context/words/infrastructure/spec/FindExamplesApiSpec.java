package es.pausegarra.russian_cheatsheet.context.words.infrastructure.spec;

import es.pausegarra.russian_cheatsheet.common.application.pagination.PaginatedDto;
import es.pausegarra.russian_cheatsheet.context.words.application.dto.ExampleSentenceDto;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.resteasy.reactive.RestResponse;

@Path("/examples")
@Tag(name = "Examples")
public interface FindExamplesApiSpec {

  @GET
  @Operation(summary = "Find example sentences")
  @APIResponse(responseCode = "200", description = "The requested page of example sentences")
  @APIResponse(responseCode = "400", description = "Invalid pagination")
  @APIResponse(responseCode = "500", description = "An unexpected error occurred")
  RestResponse<PaginatedDto<ExampleSentenceDto>> findExamples(
    @QueryParam("page") @DefaultValue("0") int page,
    @QueryParam("perPage") @DefaultValue("25") int perPage,
    @QueryParam("externalIdOnly") @DefaultValue("false")
    @Parameter(description = "Populate id, externalId, and checksum only; other fields are null for import reconciliation")
    boolean externalIdOnly
  );

}
