package es.pausegarra.russkiy_po_moyemu.vocabulary.infrastructure.rest;

import es.pausegarra.russkiy_po_moyemu.common.application.interfaces.Service;
import es.pausegarra.russkiy_po_moyemu.common.application.pagination.PaginatedDto;
import es.pausegarra.russkiy_po_moyemu.common.infrastructure.presentations.PaginatedPresentation;
import es.pausegarra.russkiy_po_moyemu.vocabulary.application.dto.WordDto;
import es.pausegarra.russkiy_po_moyemu.vocabulary.application.services.find_paginated_words.FindAllWordsPaginatedQuery;
import es.pausegarra.russkiy_po_moyemu.vocabulary.infrastructure.presentations.WordPresentation;
import es.pausegarra.russkiy_po_moyemu.vocabulary.infrastructure.spec.ListWordsApiSpec;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ListWordsResource implements ListWordsApiSpec {

  private final Service<FindAllWordsPaginatedQuery, PaginatedDto<WordDto>> service;

  public Response listWords(
      int page,
      int pageSize,
      String sortBy,
      String sortDirection
  ) {
    PaginatedDto<WordDto> words = service.handle(FindAllWordsPaginatedQuery.from(page, pageSize, sortBy, sortDirection));
    List<WordPresentation> wordProjections = words.data()
        .stream()
        .map(WordPresentation::fromDto)
        .toList();

    return Response.ok()
        .entity(PaginatedPresentation.fromDto(words, wordProjections))
        .build();
  }

}
