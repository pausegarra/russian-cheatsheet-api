package es.pausegarra.russian_cheatsheet.vocabulary.infrastructure.rest;

import es.pausegarra.russian_cheatsheet.vocabulary.application.services.create_word.CreateWordDto;
import es.pausegarra.russian_cheatsheet.vocabulary.application.services.create_word.CreateWordService;
import es.pausegarra.russian_cheatsheet.vocabulary.application.services.create_word.WordConjugationsDto;
import es.pausegarra.russian_cheatsheet.vocabulary.infrastructure.requests.CreateWordRequest;
import es.pausegarra.russian_cheatsheet.vocabulary.infrastructure.spec.CreateWordApiSpec;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.jboss.resteasy.reactive.RestResponse;

import java.net.URI;
import java.util.UUID;

@RequiredArgsConstructor
public class CreateWordResource implements CreateWordApiSpec {

  private final CreateWordService createWordService;

  @RolesAllowed("words#create")
  public RestResponse<String> createWord(
    CreateWordRequest request
  ) {
    WordConjugationsDto conjugationsDto = null;
    if (request.conjugations() != null) {
      conjugationsDto = request.conjugations().toDto();
    }
    CreateWordDto command = CreateWordDto.from(request.russian(), request.english(), request.spanish(), request.type(), conjugationsDto);

    UUID createdWordId = createWordService.handle(command);

    URI location = URI.create("/words/" + createdWordId);
    return RestResponse.created(location);
  }

}
