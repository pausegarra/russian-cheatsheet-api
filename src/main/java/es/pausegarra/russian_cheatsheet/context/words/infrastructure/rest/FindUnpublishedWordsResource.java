package es.pausegarra.russian_cheatsheet.context.words.infrastructure.rest;

import es.pausegarra.russian_cheatsheet.common.application.pagination.PaginatedDto;
import es.pausegarra.russian_cheatsheet.common.application.use_cases.UseCase;
import es.pausegarra.russian_cheatsheet.context.words.application.dto.WordDto;
import es.pausegarra.russian_cheatsheet.context.words.application.use_cases.find_unpublished.FindUnpublishedWordsDto;
import es.pausegarra.russian_cheatsheet.context.words.infrastructure.presentations.ListWordsPresentation;
import es.pausegarra.russian_cheatsheet.context.words.infrastructure.spec.FindUnpublishedWordsApiSpec;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.jboss.resteasy.reactive.RestResponse;

import java.util.List;

@RequiredArgsConstructor
public class FindUnpublishedWordsResource implements FindUnpublishedWordsApiSpec {

  private final UseCase<FindUnpublishedWordsDto, PaginatedDto<WordDto>> findUnpublishedWordsUseCase;

  @Override
  @RolesAllowed("words#publish")
  public RestResponse<PaginatedDto<ListWordsPresentation>> findPublishedWords(
    int page,
    int perPage,
    String sortBy,
    String sortDirection,
    String search
  ) {
    FindUnpublishedWordsDto dto = new FindUnpublishedWordsDto(page, perPage, sortBy, sortDirection, search);
    PaginatedDto<WordDto> result = findUnpublishedWordsUseCase.handle(dto);
    List<ListWordsPresentation> presentations = result.data().stream().map(ListWordsPresentation::fromDto).toList();
    PaginatedDto<ListWordsPresentation> paginatedDto = new PaginatedDto<>(
      presentations,
      result.page(),
      result.pageSize(),
      result.totalPages(),
      result.totalElements(),
      result.hasNextPage(),
      result.hasPreviousPage()
    );

    return RestResponse.status(RestResponse.Status.OK, paginatedDto);
  }

}
