package es.pausegarra.russian_cheatsheet.context.words.infrastructure.spec;

import es.pausegarra.russian_cheatsheet.context.words.application.dto.WordRelationDto;
import es.pausegarra.russian_cheatsheet.context.words.application.dto.WordRelationInputDto;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.ParameterIn;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.resteasy.reactive.RestResponse;

import java.util.List;
import java.util.UUID;

@Path("/words/{wordId}/relations")
@Tag(name = "Word Relations")
public interface WordRelationsApiSpec {

  @GET
  @Operation(summary = "List outgoing relations for a word")
  @APIResponse(responseCode = "200", description = "The outgoing relations")
  @APIResponse(responseCode = "404", description = "The word does not exist")
  RestResponse<List<WordRelationDto>> findWordRelations(
    @PathParam("wordId")
    @Parameter(name = "wordId", in = ParameterIn.PATH, required = true, description = "The source word UUID")
    UUID wordId
  );

  @POST
  @Operation(summary = "Create one outgoing relation")
  @APIResponse(responseCode = "200", description = "An identical relation already exists")
  @APIResponse(responseCode = "201", description = "The relation was created")
  @APIResponse(responseCode = "400", description = "The relation is invalid")
  @APIResponse(responseCode = "401", description = "The user is not authenticated")
  @APIResponse(responseCode = "403", description = "The user is not authorized to create relations")
  @APIResponse(responseCode = "404", description = "The source or target word does not exist")
  @SecurityRequirement(name = "SecurityScheme")
  RestResponse<WordRelationDto> createWordRelation(
    @PathParam("wordId")
    @Parameter(name = "wordId", in = ParameterIn.PATH, required = true, description = "The source word UUID")
    UUID wordId,

    @RequestBody
    WordRelationInputDto request
  );

  @DELETE
  @Path("/{relationId}")
  @Operation(summary = "Delete one outgoing relation")
  @APIResponse(responseCode = "204", description = "The directed relation was deleted")
  @APIResponse(responseCode = "401", description = "The user is not authenticated")
  @APIResponse(responseCode = "403", description = "The user is not authorized to delete relations")
  @APIResponse(responseCode = "404", description = "The word or owned relation does not exist")
  @SecurityRequirement(name = "SecurityScheme")
  RestResponse<Void> deleteWordRelation(
    @PathParam("wordId")
    @Parameter(name = "wordId", in = ParameterIn.PATH, required = true, description = "The source word UUID")
    UUID wordId,

    @PathParam("relationId")
    @Parameter(name = "relationId", in = ParameterIn.PATH, required = true, description = "The relation row UUID")
    UUID relationId
  );

}
