package es.pausegarra.russkiy_po_moyemu.alphabet.infrastructure.rest;

import es.pausegarra.russkiy_po_moyemu.alphabet.application.dtos.LetterDto;
import es.pausegarra.russkiy_po_moyemu.alphabet.application.mappers.LetterMapper;
import es.pausegarra.russkiy_po_moyemu.alphabet.application.services.list_letters.ListLettersQuery;
import es.pausegarra.russkiy_po_moyemu.alphabet.infrastructure.projections.LetterProjection;
import es.pausegarra.russkiy_po_moyemu.common.application.interfaces.Service;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Path("/letters")
@RequiredArgsConstructor
public class ListLettersResource {

  private final Service<ListLettersQuery, List<LetterDto>> service;

  private final LetterMapper letterMapper;

  @GET
  public Response listLetters() {
    List<LetterDto> letters = service.handle(ListLettersQuery.from());
    List<LetterProjection> letterProjections = letters.stream()
        .map(letterMapper::fromDtoToProjection)
        .toList();

    return Response.ok(letterProjections)
        .build();
  }

}
