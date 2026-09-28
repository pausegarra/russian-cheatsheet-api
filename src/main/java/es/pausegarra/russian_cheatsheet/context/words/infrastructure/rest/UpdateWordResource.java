package es.pausegarra.russian_cheatsheet.context.words.infrastructure.rest;

import es.pausegarra.russian_cheatsheet.common.application.use_cases.UseCase;
import es.pausegarra.russian_cheatsheet.context.words.application.dto.WordDto;
import es.pausegarra.russian_cheatsheet.context.words.application.use_cases.update_word.UpdateWordDto;
import es.pausegarra.russian_cheatsheet.context.words.infrastructure.requests.UpdateWordRequest;
import es.pausegarra.russian_cheatsheet.context.words.infrastructure.spec.UpdateWordApiSpec;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.jboss.resteasy.reactive.RestResponse;

import java.util.UUID;

@RequiredArgsConstructor
public class UpdateWordResource implements UpdateWordApiSpec {

  private final UseCase<UpdateWordDto, WordDto> updateWordUseCase;

  @Override
  @RolesAllowed("words#update")
  public RestResponse<WordDto> updateWord(String wordId, UpdateWordRequest request) {
    UpdateWordDto updateWordDto = new UpdateWordDto(
      UUID.fromString(wordId),
      request.russian(),
      request.externalId(),
      request.translations(),
      request.usage(),
      request.audioUrl(),
      request.type(),
      request.aspect(),
      request.forms()
    );
    WordDto wordDto = updateWordUseCase.handle(updateWordDto);

    return RestResponse.status(RestResponse.Status.OK, wordDto);
  }

}
