package es.pausegarra.russian_cheatsheet.context.words.application.use_cases.update_examples_batch;

import es.pausegarra.russian_cheatsheet.common.application.use_cases.UseCase;
import es.pausegarra.russian_cheatsheet.common.domain.exception.BadRequest;
import es.pausegarra.russian_cheatsheet.context.words.application.ExampleChecksumService;
import es.pausegarra.russian_cheatsheet.context.words.application.ExampleTranslationMapper;
import es.pausegarra.russian_cheatsheet.context.words.application.dto.ExampleSentenceDto;
import es.pausegarra.russian_cheatsheet.context.words.application.use_cases.update_example.UpdateExampleDto;
import es.pausegarra.russian_cheatsheet.context.words.domain.entities.ExampleSentenceEntity;
import es.pausegarra.russian_cheatsheet.context.words.domain.exception.ExampleNotFound;
import es.pausegarra.russian_cheatsheet.context.words.domain.repositories.ExampleSentencesRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@ApplicationScoped
@RequiredArgsConstructor
public class UpdateExamplesBatchService implements UseCase<List<UpdateExampleDto>, List<ExampleSentenceDto>> {

  private final ExampleSentencesRepository exampleSentencesRepository;
  private final ExampleChecksumService checksumService;

  @Override
  @Transactional
  public List<ExampleSentenceDto> handle(List<UpdateExampleDto> dtos) {
    List<UUID> ids = validateAndGetIds(dtos);
    Map<UUID, ExampleSentenceEntity> existingExamples = indexById(exampleSentencesRepository.findAllByIds(ids));
    for (UUID id : ids) {
      if (!existingExamples.containsKey(id)) {
        throw new ExampleNotFound(id.toString());
      }
    }

    List<ExampleSentenceEntity> updates = dtos.stream()
      .map(dto -> {
        ExampleSentenceEntity updated = existingExamples.get(dto.id()).replaceContent(
          dto.russian(), ExampleTranslationMapper.fromInputs(dto.translations()), dto.contributor(),
          dto.audioUrl(), dto.linkedWordIds()
        );
        return updated.withChecksum(checksumService.calculate(updated));
      })
      .toList();
    Map<UUID, ExampleSentenceEntity> savedExamples = indexById(exampleSentencesRepository.save(updates));

    return dtos.stream()
      .map(dto -> ExampleSentenceDto.fromEntity(savedExamples.get(dto.id())))
      .toList();
  }

  private List<UUID> validateAndGetIds(List<UpdateExampleDto> dtos) {
    if (dtos == null || dtos.isEmpty()) {
      throw new BadRequest("Batch must contain at least one item");
    }
    Set<UUID> uniqueIds = new HashSet<>();
    for (UpdateExampleDto dto : dtos) {
      if (dto == null || dto.id() == null) {
        throw new BadRequest("Every example update must include an id");
      }
      if (!uniqueIds.add(dto.id())) {
        throw new BadRequest("Batch cannot update the same example more than once");
      }
    }
    return dtos.stream().map(UpdateExampleDto::id).toList();
  }

  private Map<UUID, ExampleSentenceEntity> indexById(List<ExampleSentenceEntity> examples) {
    Map<UUID, ExampleSentenceEntity> result = new HashMap<>();
    examples.forEach(example -> result.put(example.id(), example));
    return result;
  }
}
