package es.pausegarra.russian_cheatsheet.vocabulary.infrastructure.rest;

import es.pausegarra.russian_cheatsheet.common.application.interfaces.Service;
import es.pausegarra.russian_cheatsheet.vocabulary.application.services.delete_word.DeleteWordDto;
import es.pausegarra.russian_cheatsheet.vocabulary.infrastructure.spec.DeleteWordApiSpec;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DeleteWordResource implements DeleteWordApiSpec {

  private final Service<DeleteWordDto, Void> service;

  @Override
  @RolesAllowed("words#delete")
  public Response deleteWord(String id) {
    DeleteWordDto dto = DeleteWordDto.from(id);
    service.handle(dto);

    return Response.noContent()
      .build();
  }

}
