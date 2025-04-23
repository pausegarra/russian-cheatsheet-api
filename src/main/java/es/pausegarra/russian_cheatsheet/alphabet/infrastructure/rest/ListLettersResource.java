package es.pausegarra.russian_cheatsheet.alphabet.infrastructure.rest;

import es.pausegarra.russian_cheatsheet.alphabet.application.dtos.LetterDto;
import es.pausegarra.russian_cheatsheet.alphabet.application.services.list_letters.ListLettersDto;
import es.pausegarra.russian_cheatsheet.alphabet.infrastructure.projections.LetterPresentation;
import es.pausegarra.russian_cheatsheet.alphabet.infrastructure.spec.ListLettersApiSpec;
import es.pausegarra.russian_cheatsheet.common.application.interfaces.Service;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ListLettersResource implements ListLettersApiSpec {

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
