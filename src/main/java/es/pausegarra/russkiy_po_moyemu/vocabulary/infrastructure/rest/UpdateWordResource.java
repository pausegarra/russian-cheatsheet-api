package es.pausegarra.russkiy_po_moyemu.vocabulary.infrastructure.rest;

import es.pausegarra.russkiy_po_moyemu.common.application.interfaces.Service;
import es.pausegarra.russkiy_po_moyemu.vocabulary.application.services.update_word.UpdateWordDto;
import es.pausegarra.russkiy_po_moyemu.vocabulary.infrastructure.requests.UpdateWordRequest;
import es.pausegarra.russkiy_po_moyemu.vocabulary.infrastructure.spec.UpdateWordApiSpec;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UpdateWordResource implements UpdateWordApiSpec {

  private final Service<UpdateWordDto, Void> updateWordService;

  @Override
  @RolesAllowed("words#update")
  public Response updateWord(String id, UpdateWordRequest request) {
    UpdateWordDto dto = UpdateWordDto.from(id, request.russian(), request.english(), request.spanish(), request.type());
    updateWordService.handle(dto);

    return Response.noContent()
      .build();
  }

}
