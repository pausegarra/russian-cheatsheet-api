package es.pausegarra.russkiy_po_moyemu.vocabulary.infrastructure.rest;

import es.pausegarra.russkiy_po_moyemu.common.application.interfaces.Service;
import es.pausegarra.russkiy_po_moyemu.common.application.pagination.PaginatedDto;
import es.pausegarra.russkiy_po_moyemu.common.infrastructure.presentations.PaginatedPresentation;
import es.pausegarra.russkiy_po_moyemu.vocabulary.application.dto.WordDto;
import es.pausegarra.russkiy_po_moyemu.vocabulary.application.services.find_paginated_words.FindAllWordsPaginatedQuery;
import es.pausegarra.russkiy_po_moyemu.vocabulary.infrastructure.presentations.WordPresentation;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Path("/words")
@RequiredArgsConstructor
public class ListWordsResource {

  private final Service<FindAllWordsPaginatedQuery, PaginatedDto<WordDto>> service;

  @GET
  public Response listWords(
      @QueryParam(value = "page") @DefaultValue("0") int page,
      @QueryParam(value = "pageSize") @DefaultValue("10") int pageSize,
      @QueryParam(value = "sortBy") @DefaultValue("spanish") String sortBy,
      @QueryParam(value = "sortDirection") @DefaultValue("asc") String sortDirection
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
