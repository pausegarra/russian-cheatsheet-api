package es.pausegarra.russian_cheatsheet.context.words.infrastructure.rest;

import es.pausegarra.russian_cheatsheet.common.application.use_cases.UseCase;
import es.pausegarra.russian_cheatsheet.common.domain.exception.BadRequest;
import es.pausegarra.russian_cheatsheet.context.words.application.dto.ExampleSentenceDto;
import es.pausegarra.russian_cheatsheet.context.words.application.use_cases.update_example.UpdateExampleDto;
import es.pausegarra.russian_cheatsheet.context.words.infrastructure.requests.UpdateExampleRequest;
import es.pausegarra.russian_cheatsheet.context.words.infrastructure.spec.UpdateExampleApiSpec;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.jboss.resteasy.reactive.RestResponse;

import java.util.UUID;

@RequiredArgsConstructor
public class UpdateExampleResource implements UpdateExampleApiSpec {

  private final UseCase<UpdateExampleDto, ExampleSentenceDto> updateExampleUseCase;

  @Override
  @RolesAllowed("examples#update")
  public RestResponse<ExampleSentenceDto> updateExample(String id, UpdateExampleRequest request) {
    UpdateExampleDto dto = new UpdateExampleDto(
      parseId(id),
      request.russian(),
      request.translations(),
      request.contributor(),
      request.audioUrl(),
      request.linkedWordIds()
    );
    return RestResponse.ok(updateExampleUseCase.handle(dto));
  }

  private UUID parseId(String id) {
    try {
      return UUID.fromString(id);
    } catch (IllegalArgumentException exception) {
      throw new BadRequest("Example id must be a valid UUID");
    }
  }
}
