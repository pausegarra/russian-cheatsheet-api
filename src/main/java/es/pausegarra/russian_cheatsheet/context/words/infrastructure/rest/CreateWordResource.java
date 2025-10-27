package es.pausegarra.russian_cheatsheet.context.words.infrastructure.rest;

import es.pausegarra.russian_cheatsheet.common.application.use_cases.UseCase;
import es.pausegarra.russian_cheatsheet.context.words.application.dto.requests.create_word.CreateWordDto;
import es.pausegarra.russian_cheatsheet.context.words.application.dto.responses.WordDto;
import es.pausegarra.russian_cheatsheet.context.words.infrastructure.spec.CreateWordApiSpec;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.jboss.resteasy.reactive.RestResponse;

@RequiredArgsConstructor
public class CreateWordResource implements CreateWordApiSpec {

  private final UseCase<CreateWordDto, WordDto> createWordUseCase;

  @Override
  @RolesAllowed("words#create")
  public RestResponse<WordDto> createWord(CreateWordDto createWordDto) {
    WordDto wordDto = createWordUseCase.handle(createWordDto);

    return RestResponse.status(RestResponse.Status.CREATED, wordDto);
  }

}
