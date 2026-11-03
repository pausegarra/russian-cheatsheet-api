package es.pausegarra.russian_cheatsheet.context.words.infrastructure.spec;

import es.pausegarra.russian_cheatsheet.common.application.pagination.PaginatedDto;
import es.pausegarra.russian_cheatsheet.context.words.infrastructure.presentations.ListWordsPresentation;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.ParameterIn;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.resteasy.reactive.RestResponse;

@Path("/v1/words")
@Tag(name = "Words")
public interface FindWordsByCriteriaApiSpec {

  @GET
  @Operation(
    summary = "Find Words By Criteria"
  )
  @APIResponse(
    responseCode = "200", description = "OK"
  )
  @APIResponse(
    responseCode = "400", description = "Bad Request"
  )
  @APIResponse(
    responseCode = "500", description = "Internal Server Error"
  )
  RestResponse<PaginatedDto<ListWordsPresentation>> findWordsByCriteria(
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
    String search,

    @QueryParam("matchMode")
    @Parameter(
      name = "matchMode",
      in = ParameterIn.QUERY,
      description = "precise matches complete words; contains also matches word fragments. Omit to retain legacy search behavior.",
      schema = @Schema(enumeration = { "precise", "contains" })
    )
    String matchMode
  );

}
