package es.pausegarra.russian_cheatsheet.vocabulary.infrastructure.spec;

import es.pausegarra.russian_cheatsheet.common.infrastructure.presentations.PaginatedPresentation;
import es.pausegarra.russian_cheatsheet.vocabulary.infrastructure.presentations.WordPresentation;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.ParameterIn;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.resteasy.reactive.RestResponse;

@Path("/words")
@Tag(name = "Words")
public interface ListWordsApiSpec {

  @GET
  @Operation(summary = "List words")
  @APIResponse(
    responseCode = "200",
    description = "Words found"
  )
  RestResponse<PaginatedPresentation<WordPresentation>> listWords(
    @QueryParam("page")
    @DefaultValue("0")
    @Parameter(
      name = "page",
      in = ParameterIn.QUERY
    )
    int page,

    @QueryParam("pageSize")
    @DefaultValue("10")
    @Parameter(
      name = "pageSize",
      in = ParameterIn.QUERY
    )
    int pageSize,

    @QueryParam("sortBy")
    @DefaultValue("spanish")
    @Parameter(
      name = "sortBy",
      in = ParameterIn.QUERY
    )
    String sortBy,

    @QueryParam("sortDirection")
    @DefaultValue("asc")
    @Parameter(
      name = "sortDirection",
      in = ParameterIn.QUERY
    )
    String sortDirection
  );

}
