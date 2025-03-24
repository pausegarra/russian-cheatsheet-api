package es.pausegarra.russkiy_po_moyemu.vocabulary.infrastructure.rest;

import es.pausegarra.russkiy_po_moyemu.vocabulary.application.services.create_word.CreateWordCommand;
import es.pausegarra.russkiy_po_moyemu.vocabulary.application.services.create_word.CreateWordService;
import es.pausegarra.russkiy_po_moyemu.vocabulary.infrastructure.requests.CreateWordRequest;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Path("/words")
@RequiredArgsConstructor
public class CreateWordResource {

  private final CreateWordService createWordService;

  @POST
  @RolesAllowed("words#create")
  public Response createWord(CreateWordRequest request) {
    CreateWordCommand command = CreateWordCommand.from(
        request.russian(),
        request.english(),
        request.spanish(),
        request.type()
    );

    UUID createdWordId = createWordService.handle(command);

    return Response.status(Response.Status.CREATED)
        .entity(createdWordId)
        .build();
  }

}
