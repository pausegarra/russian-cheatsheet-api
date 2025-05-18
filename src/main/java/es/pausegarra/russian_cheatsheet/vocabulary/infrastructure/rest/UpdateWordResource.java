package es.pausegarra.russian_cheatsheet.vocabulary.infrastructure.rest;

import es.pausegarra.russian_cheatsheet.common.application.interfaces.Service;
import es.pausegarra.russian_cheatsheet.common.infrastructure.presentations.SimplePresentation;
import es.pausegarra.russian_cheatsheet.vocabulary.application.dto.WordCasesDto;
import es.pausegarra.russian_cheatsheet.vocabulary.application.dto.WordConjugationsDto;
import es.pausegarra.russian_cheatsheet.vocabulary.application.services.update_word.UpdateWordDto;
import es.pausegarra.russian_cheatsheet.vocabulary.infrastructure.requests.UpdateWordRequest;
import es.pausegarra.russian_cheatsheet.vocabulary.infrastructure.spec.UpdateWordApiSpec;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.jboss.resteasy.reactive.RestResponse;

@RequiredArgsConstructor
public class UpdateWordResource implements UpdateWordApiSpec {

  private final Service<UpdateWordDto, Void> updateWordService;

  @Override
  @RolesAllowed("words#update")
  public RestResponse<SimplePresentation> updateWord(String id, UpdateWordRequest request) {
    WordConjugationsDto conjugationsDto = request.conjugations() != null ? request.conjugations()
      .toDto() : null;
    WordCasesDto casesDto = request.cases() != null ? request.cases()
      .toDto() : null;

    UpdateWordDto dto = UpdateWordDto.from(
      id,
      request.russian(),
      request.english(),
      request.spanish(),
      request.type(),
      conjugationsDto,
      casesDto
    );
    updateWordService.handle(dto);

    SimplePresentation simplePresentation = new SimplePresentation(id);
    return RestResponse.status(Response.Status.OK, simplePresentation);
  }

}
