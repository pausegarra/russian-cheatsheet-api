package es.pausegarra.russian_cheatsheet.context.words.infrastructure.rest;

import es.pausegarra.russian_cheatsheet.common.application.use_cases.UseCase;
import es.pausegarra.russian_cheatsheet.common.domain.exception.BadRequest;
import es.pausegarra.russian_cheatsheet.context.words.application.dto.WordDto;
import es.pausegarra.russian_cheatsheet.context.words.application.use_cases.create_word.CreateWordDto;
import es.pausegarra.russian_cheatsheet.context.words.application.use_cases.update_word.UpdateWordDto;
import es.pausegarra.russian_cheatsheet.context.words.infrastructure.config.BatchConfig;
import es.pausegarra.russian_cheatsheet.context.words.infrastructure.requests.UpdateWordBatchRequest;
import es.pausegarra.russian_cheatsheet.context.words.infrastructure.spec.WordsBatchApiSpec;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.jboss.resteasy.reactive.RestResponse;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
public class WordsBatchResource implements WordsBatchApiSpec {

  private final UseCase<List<CreateWordDto>, List<WordDto>> createWordsBatchService;
  private final UseCase<List<UpdateWordDto>, List<WordDto>> updateWordsBatchService;
  private final BatchConfig batchConfig;

  @Override
  @RolesAllowed("words#create")
  public RestResponse<List<WordDto>> createWords(List<CreateWordDto> requests) {
    validateBatchSize(requests);
    return RestResponse.status(RestResponse.Status.CREATED, createWordsBatchService.handle(requests));
  }

  @Override
  @RolesAllowed("words#update")
  public RestResponse<List<WordDto>> updateWords(List<UpdateWordBatchRequest> requests) {
    validateBatchSize(requests);
    List<UpdateWordDto> updates = requests.stream()
      .map(request -> new UpdateWordDto(
        request.id(), request.russian(), request.externalId(), request.translations(), request.usage(),
        request.audioUrl(), request.type(), request.aspect(), request.forms()
      ))
      .toList();
    return RestResponse.ok(updateWordsBatchService.handle(updates));
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
