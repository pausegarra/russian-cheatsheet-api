package es.pausegarra.russian_cheatsheet.vocabulary.infrastructure.rest;

import es.pausegarra.russian_cheatsheet.common.application.interfaces.Service;
import es.pausegarra.russian_cheatsheet.common.application.pagination.PaginatedDto;
import es.pausegarra.russian_cheatsheet.common.infrastructure.presentations.PaginatedPresentation;
import es.pausegarra.russian_cheatsheet.vocabulary.application.dto.WordDto;
import es.pausegarra.russian_cheatsheet.vocabulary.application.services.find_paginated_words.FindAllWordsPaginatedDto;
import es.pausegarra.russian_cheatsheet.vocabulary.infrastructure.presentations.WordListPresentation;
import es.pausegarra.russian_cheatsheet.vocabulary.infrastructure.spec.ListWordsApiSpec;
import lombok.RequiredArgsConstructor;
import org.jboss.resteasy.reactive.RestResponse;

import java.util.List;

@RequiredArgsConstructor
public class ListWordsResource implements ListWordsApiSpec {

  private final Service<FindAllWordsPaginatedDto, PaginatedDto<WordDto>> service;

  public RestResponse<PaginatedPresentation<WordListPresentation>> listWords(
    int page,
    int pageSize,
    String sortBy,
    String sortDirection,
    String search
  ) {
    PaginatedDto<WordDto> words = service.handle(FindAllWordsPaginatedDto.from(
      page,
      pageSize,
      sortBy,
      sortDirection,
      search
    ));
    List<WordListPresentation> wordProjections = words.data()
      .stream()
      .map(WordListPresentation::fromDto)
      .toList();

    PaginatedPresentation<WordListPresentation> paginatedPresentation =
      PaginatedPresentation.fromDto(
      words,
      wordProjections
    );
    return RestResponse.ok(paginatedPresentation);
  }

}
