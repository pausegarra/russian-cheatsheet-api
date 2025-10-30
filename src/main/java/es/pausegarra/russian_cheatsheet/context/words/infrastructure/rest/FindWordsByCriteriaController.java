package es.pausegarra.russian_cheatsheet.context.words.infrastructure.rest;

import es.pausegarra.russian_cheatsheet.common.application.pagination.PaginatedDto;
import es.pausegarra.russian_cheatsheet.common.application.use_cases.UseCase;
import es.pausegarra.russian_cheatsheet.context.words.application.dto.requests.find_words_by_criteria.FindWordsByCriteriaDto;
import es.pausegarra.russian_cheatsheet.context.words.application.dto.responses.WordDto;
import es.pausegarra.russian_cheatsheet.context.words.infrastructure.spec.FindWordsByCriteriaApiSpec;
import lombok.RequiredArgsConstructor;
import org.jboss.resteasy.reactive.RestResponse;

@RequiredArgsConstructor
public class FindWordsByCriteriaController implements FindWordsByCriteriaApiSpec {

  private final UseCase<FindWordsByCriteriaDto, PaginatedDto<WordDto>> useCase;

  @Override
  public RestResponse<PaginatedDto<WordDto>> findWordsByCriteria(
    int page,
    int perPage,
    String sortBy,
    String sortDirection,
    String search
  ) {
    FindWordsByCriteriaDto dto = new FindWordsByCriteriaDto(page, perPage, sortBy, sortDirection, search);
    PaginatedDto<WordDto> result = useCase.handle(dto);

    return RestResponse.status(RestResponse.Status.OK, result);
  }

}
