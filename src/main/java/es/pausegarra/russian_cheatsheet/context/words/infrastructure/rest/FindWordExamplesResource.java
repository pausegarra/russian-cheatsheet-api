package es.pausegarra.russian_cheatsheet.context.words.infrastructure.rest;

import es.pausegarra.russian_cheatsheet.common.application.pagination.PaginatedDto;
import es.pausegarra.russian_cheatsheet.common.application.use_cases.UseCase;
import es.pausegarra.russian_cheatsheet.context.words.application.dto.ExampleSentenceDto;
import es.pausegarra.russian_cheatsheet.context.words.application.use_cases.find_examples.FindExamplesDto;
import es.pausegarra.russian_cheatsheet.context.words.infrastructure.spec.FindWordExamplesApiSpec;
import lombok.RequiredArgsConstructor;
import org.jboss.resteasy.reactive.RestResponse;

import java.util.UUID;

@RequiredArgsConstructor
public class FindWordExamplesResource implements FindWordExamplesApiSpec {

  private final UseCase<FindExamplesDto, PaginatedDto<ExampleSentenceDto>> findExamplesUseCase;

  @Override
  public RestResponse<PaginatedDto<ExampleSentenceDto>> findWordExamples(String wordId, int page, int perPage) {
    FindExamplesDto dto = new FindExamplesDto(UUID.fromString(wordId), page, perPage);
    return RestResponse.ok(findExamplesUseCase.handle(dto));
  }

}
