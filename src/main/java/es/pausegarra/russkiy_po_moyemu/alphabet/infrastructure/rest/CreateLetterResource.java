package es.pausegarra.russkiy_po_moyemu.alphabet.infrastructure.rest;

import es.pausegarra.russkiy_po_moyemu.alphabet.application.services.create_letter.CreateLetterCommand;
import es.pausegarra.russkiy_po_moyemu.alphabet.infrastructure.requests.CreateLetterRequest;
import es.pausegarra.russkiy_po_moyemu.common.application.interfaces.Service;
import io.quarkus.security.identity.SecurityIdentity;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

@Path("/letters")
@RequiredArgsConstructor
@Slf4j
public class CreateLetterResource {

  private final Service<CreateLetterCommand, UUID> service;

  private final SecurityIdentity identity;

  @POST
  @RolesAllowed("letters:create")
  public Response createLetter(CreateLetterRequest request) {
    CreateLetterCommand command = CreateLetterCommand.from(request.cyrillic(), request.ipa(), request.latin());
    UUID createdLetterId = service.handle(command);

    return Response.status(Response.Status.CREATED)
        .entity(createdLetterId)
        .build();
  }

}
