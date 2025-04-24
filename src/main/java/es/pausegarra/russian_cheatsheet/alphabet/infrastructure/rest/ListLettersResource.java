package es.pausegarra.russian_cheatsheet.alphabet.infrastructure.rest;

import es.pausegarra.russian_cheatsheet.alphabet.application.dtos.LetterDto;
import es.pausegarra.russian_cheatsheet.alphabet.application.services.list_letters.ListLettersDto;
import es.pausegarra.russian_cheatsheet.alphabet.infrastructure.projections.LetterPresentation;
import es.pausegarra.russian_cheatsheet.alphabet.infrastructure.spec.ListLettersApiSpec;
import es.pausegarra.russian_cheatsheet.common.application.interfaces.Service;
import lombok.RequiredArgsConstructor;
import org.jboss.resteasy.reactive.RestResponse;

import java.util.List;

@RequiredArgsConstructor
public class ListLettersResource implements ListLettersApiSpec {

  private final Service<ListLettersDto, List<LetterDto>> service;

  public RestResponse<List<LetterPresentation>> listLetters() {
    List<LetterDto> letters = service.handle(ListLettersDto.from());
    List<LetterPresentation> letterPresentations = letters.stream()
      .map(LetterPresentation::fromDto)
      .toList();

    return RestResponse.ok(letterPresentations);
  }

}
