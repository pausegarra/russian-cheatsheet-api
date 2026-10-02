package es.pausegarra.russian_cheatsheet.context.words.infrastructure.rest;

import es.pausegarra.russian_cheatsheet.common.application.use_cases.UseCase;
import es.pausegarra.russian_cheatsheet.context.words.application.dto.ExampleSentenceDto;
import es.pausegarra.russian_cheatsheet.context.words.application.use_cases.create_example.CreateExampleDto;
import es.pausegarra.russian_cheatsheet.context.words.infrastructure.spec.CreateExampleApiSpec;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.jboss.resteasy.reactive.RestResponse;

@RequiredArgsConstructor
public class CreateExampleResource implements CreateExampleApiSpec {

  private final UseCase<CreateExampleDto, ExampleSentenceDto> createExampleUseCase;

  @Override
  @RolesAllowed("examples#create")
  public RestResponse<ExampleSentenceDto> createExample(CreateExampleDto request) {
    return RestResponse.status(RestResponse.Status.CREATED, createExampleUseCase.handle(request));
  }
}
