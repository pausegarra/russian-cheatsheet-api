package es.pausegarra.russian_cheatsheet.context.words.infrastructure.rest;

import es.pausegarra.russian_cheatsheet.common.application.use_cases.UseCase;
import es.pausegarra.russian_cheatsheet.context.words.application.dto.WordDto;
import es.pausegarra.russian_cheatsheet.context.words.application.use_cases.find_word_by_id.FindWordByIdDto;
import es.pausegarra.russian_cheatsheet.context.words.infrastructure.spec.FindWordByIdApiSpec;
import lombok.RequiredArgsConstructor;
import org.jboss.resteasy.reactive.RestResponse;

@RequiredArgsConstructor
public class FindWordByIdResource implements FindWordByIdApiSpec {

  private final UseCase<FindWordByIdDto, WordDto> service;

  @Override
  public RestResponse<WordDto> findWordById(String id) {
    FindWordByIdDto dto = FindWordByIdDto.from(id);
    WordDto word = service.handle(dto);

    return RestResponse.ok(word);
  }

}
