package es.pausegarra.russian_cheatsheet.context.words.infrastructure.spec;

import es.pausegarra.russian_cheatsheet.common.application.pagination.PaginatedDto;
import es.pausegarra.russian_cheatsheet.context.words.application.dto.responses.WordDto;
import es.pausegarra.russian_cheatsheet.context.words.infrastructure.presentations.WordPresentation;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.resteasy.reactive.RestResponse;

@Path("/words")
@Tag(name = "Words")
public interface FindWordsByCriteriaApiSpec {

  @GET
  @Operation(
    summary = "Find Words By Criteria"
  )
  @APIResponse(
    responseCode = "200",
    description = "OK"
  )
  @APIResponse(
    responseCode = "400",
    description = "Bad Request"
  )
  @APIResponse(
    responseCode = "500",
    description = "Internal Server Error"
  )
  RestResponse<PaginatedDto<WordPresentation>> findWordsByCriteria(
    @QueryParam("page")
    @DefaultValue("0")
    int page,

    @QueryParam("perPage")
    @DefaultValue("10")
    int perPage,

    @QueryParam("sortBy")
    @DefaultValue("russian")
    String sortBy,

    @QueryParam("sortDirection")
    @DefaultValue("ASC")
    String sortDirection,

    @QueryParam("search")
    @DefaultValue("")
    String search
  );

}
