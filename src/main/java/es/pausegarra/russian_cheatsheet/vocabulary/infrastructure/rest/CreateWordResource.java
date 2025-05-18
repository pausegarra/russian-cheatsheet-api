package es.pausegarra.russian_cheatsheet.vocabulary.infrastructure.rest;

import es.pausegarra.russian_cheatsheet.common.infrastructure.presentations.SimplePresentation;
import es.pausegarra.russian_cheatsheet.vocabulary.application.dto.WordCasesDto;
import es.pausegarra.russian_cheatsheet.vocabulary.application.dto.WordConjugationsDto;
import es.pausegarra.russian_cheatsheet.vocabulary.application.services.create_word.CreateWordDto;
import es.pausegarra.russian_cheatsheet.vocabulary.application.services.create_word.CreateWordService;
import es.pausegarra.russian_cheatsheet.vocabulary.infrastructure.requests.CreateWordRequest;
import es.pausegarra.russian_cheatsheet.vocabulary.infrastructure.spec.CreateWordApiSpec;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.jboss.resteasy.reactive.RestResponse;

import java.util.UUID;

@RequiredArgsConstructor
public class CreateWordResource implements CreateWordApiSpec {

  private final CreateWordService createWordService;

  @RolesAllowed("words#create")
  public RestResponse<SimplePresentation> createWord(
    CreateWordRequest request
  ) {
    WordConjugationsDto conjugationsDto = request.conjugations() == null ? null :
      request.conjugations()
      .toDto();

    WordCasesDto casesDto = request.cases() == null ? null :
      request.cases()
      .toDto();

    CreateWordDto command = CreateWordDto.from(
      request.russian(),
      request.english(),
      request.spanish(),
      request.type(),
      conjugationsDto,
      casesDto
    );

    UUID createdWordId = createWordService.handle(command);
    SimplePresentation simplePresentation = new SimplePresentation(createdWordId.toString());
    return RestResponse.status(Response.Status.CREATED, simplePresentation);
  }

}
