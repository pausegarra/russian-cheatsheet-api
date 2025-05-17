package es.pausegarra.russian_cheatsheet.alphabet.infrastructure.rest;

import es.pausegarra.russian_cheatsheet.alphabet.application.services.create_letter.CreateLetterDto;
import es.pausegarra.russian_cheatsheet.alphabet.infrastructure.requests.CreateLetterRequest;
import es.pausegarra.russian_cheatsheet.alphabet.infrastructure.spec.CreateLetterApiSpec;
import es.pausegarra.russian_cheatsheet.common.application.interfaces.Service;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.jboss.resteasy.reactive.RestResponse;

import java.net.URI;
import java.util.UUID;

@RequiredArgsConstructor
public class CreateLetterResource implements CreateLetterApiSpec {

  private final Service<CreateLetterDto, UUID> service;

  @RolesAllowed("letters#create")
  public RestResponse<String> createLetter(CreateLetterRequest request) {
    CreateLetterDto command = CreateLetterDto.from(
      request.cyrillic(),
      request.ipa(),
      request.latin()
    );
    UUID createdLetterId = service.handle(command);

    URI location = URI.create("/letters/" + createdLetterId);
    return RestResponse.created(location);
  }

}
