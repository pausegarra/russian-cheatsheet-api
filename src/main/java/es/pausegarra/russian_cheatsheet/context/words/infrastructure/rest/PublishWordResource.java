package es.pausegarra.russian_cheatsheet.context.words.infrastructure.rest;

import es.pausegarra.russian_cheatsheet.common.application.use_cases.UseCase;
import es.pausegarra.russian_cheatsheet.context.words.application.use_cases.publish_word.PublishWordDto;
import es.pausegarra.russian_cheatsheet.context.words.infrastructure.spec.PublishWordApiSpec;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.jboss.resteasy.reactive.RestResponse;

@RequiredArgsConstructor
public class PublishWordResource implements PublishWordApiSpec {

  private final UseCase<PublishWordDto, Void> publishWordUseCase;

  @Override
  @RolesAllowed("words#publish")
  public RestResponse<Void> publishWord(String wordId) {
    PublishWordDto dto = PublishWordDto.from(wordId);

    publishWordUseCase.handle(dto);

    return RestResponse.status(RestResponse.Status.NO_CONTENT);
  }

}
