package es.pausegarra.russian_cheatsheet.context.words.infrastructure.rest;

import es.pausegarra.russian_cheatsheet.common.application.pagination.PaginatedDto;
import es.pausegarra.russian_cheatsheet.common.application.use_cases.UseCase;
import es.pausegarra.russian_cheatsheet.context.words.application.dto.ExampleSentenceDto;
import es.pausegarra.russian_cheatsheet.context.words.application.use_cases.find_examples.FindExamplesDto;
import es.pausegarra.russian_cheatsheet.context.words.infrastructure.spec.FindExamplesApiSpec;
import lombok.RequiredArgsConstructor;
import org.jboss.resteasy.reactive.RestResponse;

@RequiredArgsConstructor
public class FindExamplesResource implements FindExamplesApiSpec {

  private final UseCase<FindExamplesDto, PaginatedDto<ExampleSentenceDto>> findExamplesUseCase;

  @Override
  public RestResponse<PaginatedDto<ExampleSentenceDto>> findExamples(
    int page,
    int perPage,
    boolean externalIdOnly
  ) {
    return RestResponse.ok(findExamplesUseCase.handle(
      new FindExamplesDto(null, page, perPage, externalIdOnly)
    ));
  }

}
