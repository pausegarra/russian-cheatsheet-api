package es.pausegarra.russian_cheatsheet.context.words.infrastructure.spec;

import es.pausegarra.russian_cheatsheet.common.application.pagination.PaginatedDto;
import es.pausegarra.russian_cheatsheet.context.words.application.dto.ExternalChecksumDto;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.resteasy.reactive.RestResponse;

@Path("/examples/import/checksums")
@Tag(name = "Examples")
public interface FindExampleImportChecksumsApiSpec {

  @GET
  @Operation(summary = "Find checksums for all imported example sentences")
  @APIResponse(responseCode = "200", description = "The imported example IDs and checksums")
  @APIResponse(responseCode = "401", description = "The caller is not authenticated")
  @APIResponse(responseCode = "403", description = "The caller cannot read import checksums")
  @SecurityRequirement(name = "SecurityScheme")
  RestResponse<PaginatedDto<ExternalChecksumDto>> findExampleImportChecksums(
    @QueryParam("page") @DefaultValue("0") int page,
    @QueryParam("perPage") @DefaultValue("1000") int perPage
  );

}
