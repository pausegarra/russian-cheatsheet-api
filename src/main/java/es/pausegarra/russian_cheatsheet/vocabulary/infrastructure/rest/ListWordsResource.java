package es.pausegarra.russian_cheatsheet.vocabulary.infrastructure.rest;

import es.pausegarra.russian_cheatsheet.common.application.interfaces.Service;
import es.pausegarra.russian_cheatsheet.common.application.pagination.PaginatedDto;
import es.pausegarra.russian_cheatsheet.common.infrastructure.presentations.PaginatedPresentation;
import es.pausegarra.russian_cheatsheet.vocabulary.application.dto.WordDto;
import es.pausegarra.russian_cheatsheet.vocabulary.application.services.find_paginated_words.FindAllWordsPaginatedDto;
import es.pausegarra.russian_cheatsheet.vocabulary.infrastructure.presentations.WordPresentation;
import es.pausegarra.russian_cheatsheet.vocabulary.infrastructure.spec.ListWordsApiSpec;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ListWordsResource implements ListWordsApiSpec {

  private final Service<FindAllWordsPaginatedDto, PaginatedDto<WordDto>> service;

  public Response listWords(int page, int pageSize, String sortBy, String sortDirection) {
    PaginatedDto<WordDto> words = service.handle(FindAllWordsPaginatedDto.from(page, pageSize, sortBy, sortDirection));
    List<WordPresentation> wordProjections = words.data()
      .stream()
      .map(WordPresentation::fromDto)
      .toList();

    return Response.ok()
      .entity(PaginatedPresentation.fromDto(words, wordProjections))
      .build();
  }

}
