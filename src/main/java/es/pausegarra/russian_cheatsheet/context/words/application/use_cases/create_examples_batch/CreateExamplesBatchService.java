package es.pausegarra.russian_cheatsheet.context.words.application.use_cases.create_examples_batch;

import es.pausegarra.russian_cheatsheet.common.application.use_cases.UseCase;
import es.pausegarra.russian_cheatsheet.common.domain.exception.BadRequest;
import es.pausegarra.russian_cheatsheet.context.words.application.ExampleChecksumService;
import es.pausegarra.russian_cheatsheet.context.words.application.ExampleTranslationMapper;
import es.pausegarra.russian_cheatsheet.context.words.application.dto.ExampleSentenceDto;
import es.pausegarra.russian_cheatsheet.context.words.application.use_cases.create_example.CreateExampleDto;
import es.pausegarra.russian_cheatsheet.context.words.domain.entities.ExampleSentenceEntity;
import es.pausegarra.russian_cheatsheet.context.words.domain.repositories.ExampleSentencesRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.util.List;

@ApplicationScoped
@RequiredArgsConstructor
public class CreateExamplesBatchService implements UseCase<List<CreateExampleDto>, List<ExampleSentenceDto>> {

  private final ExampleSentencesRepository exampleSentencesRepository;
  private final ExampleChecksumService checksumService;

  @Override
  @Transactional
  public List<ExampleSentenceDto> handle(List<CreateExampleDto> dtos) {
    validateBatch(dtos);
    List<ExampleSentenceEntity> examples = dtos.stream()
      .map(dto -> ExampleSentenceEntity.create(
        dto.externalId(), dto.russian(), ExampleTranslationMapper.fromInputs(dto.translations()),
        dto.contributor(), dto.audioUrl(), dto.linkedWordIds()
      ))
      .map(example -> example.withChecksum(checksumService.calculate(example)))
      .toList();
    return exampleSentencesRepository.create(examples).stream().map(ExampleSentenceDto::fromEntity).toList();
  }

  private void validateBatch(List<CreateExampleDto> dtos) {
    if (dtos == null || dtos.isEmpty()) {
      throw new BadRequest("Batch must contain at least one item");
    }
    if (dtos.stream().anyMatch(dto -> dto == null)) {
      throw new BadRequest("Batch items cannot be null");
    }
  }
}
