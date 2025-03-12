package es.pausegarra.russkiy_po_moyemu.alphabet.infrastructure.rest;

import es.pausegarra.russkiy_po_moyemu.alphabet.application.dtos.LetterDto;
import es.pausegarra.russkiy_po_moyemu.alphabet.application.mappers.LetterMapper;
import es.pausegarra.russkiy_po_moyemu.alphabet.application.services.list_letters.ListLettersService;
import es.pausegarra.russkiy_po_moyemu.alphabet.infrastructure.projections.LetterProjection;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Path("/letters")
@RequiredArgsConstructor
public class ListLettersResource {

  private final ListLettersService listLettersService;

  private final LetterMapper letterMapper;

  @GET
  public Response listLetters() {
    List<LetterDto> letters = listLettersService.handle();
    List<LetterProjection> letterProjections = letters.stream()
        .map(letterMapper::fromDtoToProjection)
        .toList();

    return Response.ok(letterProjections)
        .build();
  }

}
