package es.pausegarra.russkiy_po_moyemu.alphabet.infrastructure.rest;

import es.pausegarra.russkiy_po_moyemu.alphabet.application.services.create_letter.CreateLetterCommand;
import es.pausegarra.russkiy_po_moyemu.alphabet.infrastructure.requests.CreateLetterRequest;
import es.pausegarra.russkiy_po_moyemu.alphabet.infrastructure.spec.CreateLetterApiSpec;
import es.pausegarra.russkiy_po_moyemu.common.application.interfaces.Service;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class CreateLetterResource implements CreateLetterApiSpec {

  private final Service<CreateLetterCommand, UUID> service;

  @RolesAllowed("letters#create")
  public Response createLetter(CreateLetterRequest request) {
    CreateLetterCommand command = CreateLetterCommand.from(request.cyrillic(), request.ipa(), request.latin());
    UUID createdLetterId = service.handle(command);

    return Response.status(Response.Status.CREATED)
        .entity(createdLetterId)
        .build();
  }

}
