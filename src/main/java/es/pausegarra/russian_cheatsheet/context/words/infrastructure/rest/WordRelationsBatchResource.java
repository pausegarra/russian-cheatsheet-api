package es.pausegarra.russian_cheatsheet.context.words.infrastructure.rest;

import es.pausegarra.russian_cheatsheet.common.application.use_cases.UseCase;
import es.pausegarra.russian_cheatsheet.common.domain.exception.BadRequest;
import es.pausegarra.russian_cheatsheet.context.words.application.dto.WordRelationBatchInputDto;
import es.pausegarra.russian_cheatsheet.context.words.application.dto.WordRelationBatchResultDto;
import es.pausegarra.russian_cheatsheet.context.words.infrastructure.config.BatchConfig;
import es.pausegarra.russian_cheatsheet.context.words.infrastructure.requests.WordRelationBatchRequest;
import es.pausegarra.russian_cheatsheet.context.words.infrastructure.spec.WordRelationsBatchApiSpec;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.jboss.resteasy.reactive.RestResponse;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
public class WordRelationsBatchResource implements WordRelationsBatchApiSpec {

  private final UseCase<List<WordRelationBatchInputDto>, List<WordRelationBatchResultDto>> createRelationsBatchUseCase;
  private final UseCase<List<WordRelationBatchInputDto>, Void> deleteRelationsBatchUseCase;
  private final BatchConfig batchConfig;

  @Override
  @RolesAllowed("words#create")
  public RestResponse<List<WordRelationBatchResultDto>> createRelations(List<WordRelationBatchRequest> requests) {
    validateBatchSize(requests);
    List<WordRelationBatchResultDto> results = createRelationsBatchUseCase.handle(toInputs(requests));
    return results.stream().anyMatch(WordRelationBatchResultDto::created)
      ? RestResponse.status(RestResponse.Status.CREATED, results)
      : RestResponse.ok(results);
  }

  @Override
  @RolesAllowed("words#delete")
  public RestResponse<Void> deleteRelations(List<WordRelationBatchRequest> requests) {
    validateBatchSize(requests);
    deleteRelationsBatchUseCase.handle(toInputs(requests));
    return RestResponse.status(RestResponse.Status.NO_CONTENT);
  }

  private List<WordRelationBatchInputDto> toInputs(List<WordRelationBatchRequest> requests) {
    return requests.stream()
      .map(request -> new WordRelationBatchInputDto(request.wordId(), request.relatedWordId(), request.relation()))
      .toList();
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
