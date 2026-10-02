package es.pausegarra.russian_cheatsheet.context.words.application.use_cases.update_example;

import es.pausegarra.russian_cheatsheet.common.application.use_cases.UseCase;
import es.pausegarra.russian_cheatsheet.context.words.application.ExampleChecksumService;
import es.pausegarra.russian_cheatsheet.context.words.application.ExampleTranslationMapper;
import es.pausegarra.russian_cheatsheet.context.words.application.dto.ExampleSentenceDto;
import es.pausegarra.russian_cheatsheet.context.words.domain.entities.ExampleSentenceEntity;
import es.pausegarra.russian_cheatsheet.context.words.domain.exception.ExampleNotFound;
import es.pausegarra.russian_cheatsheet.context.words.domain.repositories.ExampleSentencesRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@ApplicationScoped
@RequiredArgsConstructor
public class UpdateExampleService implements UseCase<UpdateExampleDto, ExampleSentenceDto> {

  private final ExampleSentencesRepository exampleSentencesRepository;
  private final ExampleChecksumService checksumService;

  @Override
  @Transactional
  public ExampleSentenceDto handle(UpdateExampleDto dto) {
    ExampleSentenceEntity existing = exampleSentencesRepository.findById(dto.id())
      .orElseThrow(() -> new ExampleNotFound(dto.id().toString()));
    ExampleSentenceEntity updated = existing.replaceContent(
      dto.russian(),
      ExampleTranslationMapper.fromInputs(dto.translations()),
      dto.contributor(),
      dto.audioUrl(),
      dto.linkedWordIds()
    );
    ExampleSentenceEntity saved = exampleSentencesRepository.save(
      updated.withChecksum(checksumService.calculate(updated))
    );
    return ExampleSentenceDto.fromEntity(saved);
  }
}
