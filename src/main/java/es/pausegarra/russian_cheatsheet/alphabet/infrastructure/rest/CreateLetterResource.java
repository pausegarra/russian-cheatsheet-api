package es.pausegarra.russian_cheatsheet.alphabet.infrastructure.rest;

import es.pausegarra.russian_cheatsheet.alphabet.application.services.create_letter.CreateLetterDto;
import es.pausegarra.russian_cheatsheet.alphabet.infrastructure.requests.CreateLetterRequest;
import es.pausegarra.russian_cheatsheet.alphabet.infrastructure.spec.CreateLetterApiSpec;
import es.pausegarra.russian_cheatsheet.common.application.interfaces.Service;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class CreateLetterResource implements CreateLetterApiSpec {

  private final Service<CreateLetterDto, UUID> service;

  @RolesAllowed("letters#create")
  public Response createLetter(CreateLetterRequest request) {
    CreateLetterDto command = CreateLetterDto.from(request.cyrillic(), request.ipa(), request.latin());
    UUID createdLetterId = service.handle(command);

    return Response.status(Response.Status.CREATED)
      .entity(createdLetterId)
      .build();
  }

}
