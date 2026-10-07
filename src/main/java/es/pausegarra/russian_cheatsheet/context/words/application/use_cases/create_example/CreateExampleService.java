package es.pausegarra.russian_cheatsheet.context.words.application.use_cases.create_example;

import es.pausegarra.russian_cheatsheet.common.application.use_cases.UseCase;
import es.pausegarra.russian_cheatsheet.context.words.application.ExampleChecksumService;
import es.pausegarra.russian_cheatsheet.context.words.application.ExampleTranslationMapper;
import es.pausegarra.russian_cheatsheet.context.words.application.dto.ExampleSentenceDto;
import es.pausegarra.russian_cheatsheet.context.words.domain.entities.ExampleSentenceEntity;
import es.pausegarra.russian_cheatsheet.context.words.domain.repositories.ExampleSentencesRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@ApplicationScoped
@RequiredArgsConstructor
public class CreateExampleService implements UseCase<CreateExampleDto, ExampleSentenceDto> {

  private final ExampleSentencesRepository exampleSentencesRepository;
  private final ExampleChecksumService checksumService;

  @Override
  @Transactional
  public ExampleSentenceDto handle(CreateExampleDto dto) {
    ExampleSentenceEntity example = ExampleSentenceEntity.create(
      dto.externalId(),
      dto.russian(),
      ExampleTranslationMapper.fromInputs(dto.translations()),
      dto.contributor(),
      dto.audioUrl(),
      dto.linkedWordIds()
    );
    ExampleSentenceEntity saved = exampleSentencesRepository.save(
      example.withChecksum(checksumService.calculate(example))
    );
    return ExampleSentenceDto.fromEntity(saved);
  }
}
