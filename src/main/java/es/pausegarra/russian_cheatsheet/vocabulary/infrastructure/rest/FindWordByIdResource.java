package es.pausegarra.russian_cheatsheet.vocabulary.infrastructure.rest;

import es.pausegarra.russian_cheatsheet.common.application.interfaces.Service;
import es.pausegarra.russian_cheatsheet.vocabulary.application.dto.WordDto;
import es.pausegarra.russian_cheatsheet.vocabulary.application.services.find_by_id.FindWordByIdDto;
import es.pausegarra.russian_cheatsheet.vocabulary.infrastructure.presentations.WordPresentation;
import es.pausegarra.russian_cheatsheet.vocabulary.infrastructure.spec.FindWordByIdApiSpec;
import lombok.RequiredArgsConstructor;
import org.jboss.resteasy.reactive.RestResponse;

@RequiredArgsConstructor
public class FindWordByIdResource implements FindWordByIdApiSpec {

  private final Service<FindWordByIdDto, WordDto> service;

  @Override
  public RestResponse<WordPresentation> findWordById(String id) {
    WordDto word = service.handle(FindWordByIdDto.from(id));

    WordPresentation presentation = WordPresentation.fromDto(word);
    return RestResponse.ok(presentation);
  }

}
