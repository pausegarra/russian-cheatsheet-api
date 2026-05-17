package es.pausegarra.russian_cheatsheet.context.words.infrastructure.rest;

import es.pausegarra.russian_cheatsheet.common.application.use_cases.UseCase;
import es.pausegarra.russian_cheatsheet.context.words.application.use_cases.delete_word.DeleteWordDto;
import es.pausegarra.russian_cheatsheet.context.words.infrastructure.spec.DeleteWordApiSpec;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.jboss.resteasy.reactive.RestResponse;

@RequiredArgsConstructor
public class DeleteWordResource implements DeleteWordApiSpec {

  private final UseCase<DeleteWordDto, Void> deleteWordUseCase;

  @Override
  @RolesAllowed("words#delete")
  public RestResponse<Void> deleteWord(String wordId) {
    DeleteWordDto dto = DeleteWordDto.from(wordId);

    deleteWordUseCase.handle(dto);

    return RestResponse.status(RestResponse.Status.NO_CONTENT);
  }

}
