package es.pausegarra.russkiy_po_moyemu.alphabet.infrastructure.spec;

import es.pausegarra.russkiy_po_moyemu.alphabet.infrastructure.projections.LetterPresentation;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Path("/letters")
@Tag(name = "Letters")
public interface ListLettersApiSpec {

  @GET
  @Operation(summary = "List letters")
  @APIResponse(
    responseCode = "200", description = "Letters found", content = @Content(
    mediaType = "application/json", schema = @Schema(
    implementation = LetterPresentation.class
  )
  )
  )
  Response listLetters();

}
