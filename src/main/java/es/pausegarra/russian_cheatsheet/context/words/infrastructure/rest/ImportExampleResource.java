package es.pausegarra.russian_cheatsheet.context.words.infrastructure.rest;

import es.pausegarra.russian_cheatsheet.common.application.use_cases.UseCase;
import es.pausegarra.russian_cheatsheet.context.words.application.dto.ExampleSentenceDto;
import es.pausegarra.russian_cheatsheet.context.words.application.use_cases.import_example.ImportExampleDto;
import es.pausegarra.russian_cheatsheet.context.words.application.use_cases.import_example.ImportExampleRequestDto;
import es.pausegarra.russian_cheatsheet.context.words.infrastructure.spec.ImportExampleApiSpec;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.jboss.resteasy.reactive.RestResponse;

@RequiredArgsConstructor
public class ImportExampleResource implements ImportExampleApiSpec {

  private final UseCase<ImportExampleDto, ExampleSentenceDto> importExampleUseCase;

  @Override
  @RolesAllowed("words#create")
  public RestResponse<ExampleSentenceDto> importExample(String externalId, ImportExampleRequestDto request) {
    ExampleSentenceDto example = importExampleUseCase.handle(new ImportExampleDto(externalId, request));
    return RestResponse.ok(example);
  }

}
