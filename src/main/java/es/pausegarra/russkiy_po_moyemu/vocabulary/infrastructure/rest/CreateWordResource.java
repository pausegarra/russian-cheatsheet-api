package es.pausegarra.russkiy_po_moyemu.vocabulary.infrastructure.rest;

import es.pausegarra.russkiy_po_moyemu.vocabulary.application.services.create_word.CreateWordDto;
import es.pausegarra.russkiy_po_moyemu.vocabulary.application.services.create_word.CreateWordService;
import es.pausegarra.russkiy_po_moyemu.vocabulary.infrastructure.requests.CreateWordRequest;
import es.pausegarra.russkiy_po_moyemu.vocabulary.infrastructure.spec.CreateWordApiSpec;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class CreateWordResource implements CreateWordApiSpec {

  private final CreateWordService createWordService;

  @RolesAllowed("words#create")
  public Response createWord(
    CreateWordRequest request
  ) {
    CreateWordDto command = CreateWordDto.from(request.russian(), request.english(), request.spanish(), request.type());

    UUID createdWordId = createWordService.handle(command);

    return Response.status(Response.Status.CREATED)
      .entity(createdWordId)
      .build();
  }

}
