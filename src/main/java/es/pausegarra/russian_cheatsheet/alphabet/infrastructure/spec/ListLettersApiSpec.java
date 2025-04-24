package es.pausegarra.russian_cheatsheet.alphabet.infrastructure.spec;

import es.pausegarra.russian_cheatsheet.alphabet.infrastructure.projections.LetterPresentation;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.jboss.resteasy.reactive.RestResponse;

import java.util.List;

@Path("/letters")
@Tag(name = "Letters")
public interface ListLettersApiSpec {

  @GET
  @Operation(summary = "List letters")
  @APIResponse(
    responseCode = "200",
    description = "Letters found"
  )
  RestResponse<List<LetterPresentation>> listLetters();

}
