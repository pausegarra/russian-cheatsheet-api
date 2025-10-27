package es.pausegarra.russian_cheatsheet.context.words.infrastructure.spec;

import es.pausegarra.russian_cheatsheet.context.words.application.dto.requests.create_word.CreateWordDto;
import es.pausegarra.russian_cheatsheet.context.words.application.dto.responses.WordDto;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.resteasy.reactive.RestResponse;

@Path("/words")
@Tag(name = "Words")
public interface CreateWordApiSpec {

  @Operation(summary = "Create a new word")
  @POST
  @APIResponse(
    responseCode = "201",
    description = "Created"
  )
  @APIResponse(
    responseCode = "400",
    description = "Bad Request"
  )
  @APIResponse(
    responseCode = "401",
    description = "Unauthorized"
  )
  @APIResponse(
    responseCode = "403",
    description = "Forbidden"
  )
  @APIResponse(
    responseCode = "500",
    description = "Internal Server Error"
  )
  @SecurityRequirement(
    name = "SecurityScheme"
  )
  RestResponse<WordDto> createWord(CreateWordDto createWordDto);
}
