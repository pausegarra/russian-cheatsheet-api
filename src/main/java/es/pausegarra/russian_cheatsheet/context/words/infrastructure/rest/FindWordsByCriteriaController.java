package es.pausegarra.russian_cheatsheet.context.words.infrastructure.rest;

import es.pausegarra.russian_cheatsheet.common.application.pagination.PaginatedDto;
import es.pausegarra.russian_cheatsheet.common.application.use_cases.UseCase;
import es.pausegarra.russian_cheatsheet.context.words.application.use_cases.find_words_by_criteria.FindWordsByCriteriaDto;
import es.pausegarra.russian_cheatsheet.context.words.application.dto.WordDto;
import es.pausegarra.russian_cheatsheet.context.words.infrastructure.presentations.WordPresentation;
import es.pausegarra.russian_cheatsheet.context.words.infrastructure.spec.FindWordsByCriteriaApiSpec;
import lombok.RequiredArgsConstructor;
import org.jboss.resteasy.reactive.RestResponse;

import java.util.List;

@RequiredArgsConstructor
public class FindWordsByCriteriaController implements FindWordsByCriteriaApiSpec {

  private final UseCase<FindWordsByCriteriaDto, PaginatedDto<WordDto>> useCase;

  @Override
  public RestResponse<PaginatedDto<WordPresentation>> findWordsByCriteria(
    int page,
    int perPage,
    String sortBy,
    String sortDirection,
    String search
  ) {
    FindWordsByCriteriaDto dto = new FindWordsByCriteriaDto(page, perPage, sortBy, sortDirection, search);
    PaginatedDto<WordDto> result = useCase.handle(dto);
    List<WordPresentation> presentations = result.data().stream().map(WordPresentation::fromDto).toList();
    PaginatedDto<WordPresentation> paginatedPresentations = new PaginatedDto<>(
      presentations,
      result.page(),
      result.pageSize(),
      result.totalPages(),
      result.totalElements(),
      result.hasNextPage(),
      result.hasPreviousPage()
    );

    return RestResponse.status(RestResponse.Status.OK, paginatedPresentations);
  }

}
