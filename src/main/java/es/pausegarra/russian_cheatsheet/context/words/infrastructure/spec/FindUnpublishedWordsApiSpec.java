package es.pausegarra.russian_cheatsheet.context.words.infrastructure.spec;

import es.pausegarra.russian_cheatsheet.common.application.pagination.PaginatedDto;
import es.pausegarra.russian_cheatsheet.context.words.infrastructure.presentations.ListWordsPresentation;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.resteasy.reactive.RestResponse;

@Path("/words/unpublished")
@Tag(name = "Words")
public interface FindUnpublishedWordsApiSpec {

  @GET
  @Operation(summary = "Find all published words")
  @APIResponse(responseCode = "200", description = "The list of published words")
  @APIResponse(responseCode = "401", description = "The user is not authenticated")
  @APIResponse(responseCode = "403", description = "The user is not authorized to find published words")
  @APIResponse(responseCode = "500", description = "An unexpected error occurred")
  @SecurityRequirement(name = "SecurityScheme")
  RestResponse<PaginatedDto<ListWordsPresentation>> findPublishedWords(
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
