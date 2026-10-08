package es.pausegarra.russian_cheatsheet.context.words.infrastructure.rest;

import es.pausegarra.russian_cheatsheet.common.application.pagination.PaginatedDto;
import es.pausegarra.russian_cheatsheet.common.application.use_cases.UseCase;
import es.pausegarra.russian_cheatsheet.context.words.application.dto.WordRelationListItemDto;
import es.pausegarra.russian_cheatsheet.context.words.application.use_cases.find_all_word_relations.FindAllWordRelationsDto;
import es.pausegarra.russian_cheatsheet.context.words.infrastructure.spec.FindAllWordRelationsApiSpec;
import lombok.RequiredArgsConstructor;
import org.jboss.resteasy.reactive.RestResponse;

@RequiredArgsConstructor
public class FindAllWordRelationsResource implements FindAllWordRelationsApiSpec {

  private final UseCase<FindAllWordRelationsDto, PaginatedDto<WordRelationListItemDto>> useCase;

  @Override
  public RestResponse<PaginatedDto<WordRelationListItemDto>> findAllWordRelations(int page, int perPage) {
    return RestResponse.ok(useCase.handle(new FindAllWordRelationsDto(page, perPage)));
  }

}
