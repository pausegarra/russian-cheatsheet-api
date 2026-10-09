package es.pausegarra.russian_cheatsheet.context.words.infrastructure.rest;

import es.pausegarra.russian_cheatsheet.common.application.use_cases.UseCase;
import es.pausegarra.russian_cheatsheet.common.domain.exception.BadRequest;
import es.pausegarra.russian_cheatsheet.context.words.application.dto.ExampleSentenceDto;
import es.pausegarra.russian_cheatsheet.context.words.application.use_cases.create_example.CreateExampleDto;
import es.pausegarra.russian_cheatsheet.context.words.application.use_cases.delete_examples_batch.DeleteExamplesBatchService;
import es.pausegarra.russian_cheatsheet.context.words.application.use_cases.update_example.UpdateExampleDto;
import es.pausegarra.russian_cheatsheet.context.words.infrastructure.config.BatchConfig;
import es.pausegarra.russian_cheatsheet.context.words.infrastructure.requests.UpdateExampleBatchRequest;
import es.pausegarra.russian_cheatsheet.context.words.infrastructure.spec.ExamplesBatchApiSpec;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.jboss.resteasy.reactive.RestResponse;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RequiredArgsConstructor
public class ExamplesBatchResource implements ExamplesBatchApiSpec {

  private final UseCase<List<CreateExampleDto>, List<ExampleSentenceDto>> createExamplesBatchService;
  private final UseCase<List<UpdateExampleDto>, List<ExampleSentenceDto>> updateExamplesBatchService;
  private final DeleteExamplesBatchService deleteExamplesBatchService;
  private final BatchConfig batchConfig;

  @Override
  @RolesAllowed("examples#create")
  public RestResponse<List<ExampleSentenceDto>> createExamples(List<CreateExampleDto> requests) {
    validateBatchSize(requests);
    return RestResponse.status(RestResponse.Status.CREATED, createExamplesBatchService.handle(requests));
  }

  @Override
  @RolesAllowed("examples#update")
  public RestResponse<List<ExampleSentenceDto>> updateExamples(List<UpdateExampleBatchRequest> requests) {
    validateBatchSize(requests);
    List<UpdateExampleDto> updates = requests.stream()
      .map(request -> new UpdateExampleDto(
        request.id(), request.russian(), request.translations(), request.contributor(),
        request.audioUrl(), request.linkedWordIds()
      ))
      .toList();
    return RestResponse.ok(updateExamplesBatchService.handle(updates));
  }

  @Override
  @RolesAllowed("examples#delete")
  public RestResponse<Void> deleteExamples(List<UUID> ids) {
    validateBatchSize(ids);
    deleteExamplesBatchService.handle(ids);
    return RestResponse.status(RestResponse.Status.NO_CONTENT);
  }

  private void validateBatchSize(List<?> requests) {
    if (requests == null || requests.isEmpty()) {
      throw new BadRequest("Batch must contain at least one item");
    }
    if (requests.stream().anyMatch(Objects::isNull)) {
      throw new BadRequest("Batch items cannot be null");
    }
    if (requests.size() > batchConfig.maxSize()) {
      throw new BadRequest("Batch exceeds maximum size of " + batchConfig.maxSize());
    }
  }
}
