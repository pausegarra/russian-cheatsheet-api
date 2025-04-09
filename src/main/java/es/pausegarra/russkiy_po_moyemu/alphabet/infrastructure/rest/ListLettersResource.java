package es.pausegarra.russkiy_po_moyemu.alphabet.infrastructure.rest;

import es.pausegarra.russkiy_po_moyemu.alphabet.application.dtos.LetterDto;
import es.pausegarra.russkiy_po_moyemu.alphabet.application.services.list_letters.ListLettersDto;
import es.pausegarra.russkiy_po_moyemu.alphabet.infrastructure.projections.LetterPresentation;
import es.pausegarra.russkiy_po_moyemu.alphabet.infrastructure.spec.ListLettersApiSpec;
import es.pausegarra.russkiy_po_moyemu.common.application.interfaces.Service;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ListLettersResource  implements ListLettersApiSpec {

  private final Service<ListLettersDto, List<LetterDto>> service;

  public Response listLetters() {
    List<LetterDto> letters = service.handle(ListLettersDto.from());
    List<LetterPresentation> letterPresentations = letters.stream()
        .map(LetterPresentation::fromDto)
        .toList();

    return Response.ok(letterPresentations)
        .build();
  }

}
