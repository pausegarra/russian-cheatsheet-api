package es.pausegarra.russian_cheatsheet.context.words.infrastructure.spec;

import es.pausegarra.russian_cheatsheet.common.application.pagination.PaginatedDto;
import es.pausegarra.russian_cheatsheet.context.words.application.dto.WordRelationListItemDto;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.resteasy.reactive.RestResponse;

@Path("/api/v1/words/relations")
@Tag(name = "Word Relations")
public interface FindAllWordRelationsApiSpec {

  @GET
  @Operation(summary = "List all word relations")
  @APIResponse(responseCode = "200", description = "The requested page of word relations")
  @APIResponse(responseCode = "400", description = "Invalid pagination")
  RestResponse<PaginatedDto<WordRelationListItemDto>> findAllWordRelations(
    @QueryParam("page")
    @DefaultValue("0")
    @Parameter(description = "Zero-based page index")
    int page,

    @QueryParam("perPage")
    @DefaultValue("10")
    @Parameter(description = "Maximum number of relations per page (1–1000)")
    int perPage
  );

}
