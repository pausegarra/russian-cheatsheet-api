package es.pausegarra.russian_cheatsheet.context.words.application.use_cases.import_example;

import es.pausegarra.russian_cheatsheet.common.application.use_cases.UseCase;
import es.pausegarra.russian_cheatsheet.common.domain.exception.BadRequest;
import es.pausegarra.russian_cheatsheet.context.words.application.ExampleChecksumService;
import es.pausegarra.russian_cheatsheet.context.words.application.dto.ExampleSentenceDto;
import es.pausegarra.russian_cheatsheet.context.words.domain.entities.ExampleSentenceEntity;
import es.pausegarra.russian_cheatsheet.context.words.domain.entities.ExampleTranslationEntity;
import es.pausegarra.russian_cheatsheet.context.words.domain.repositories.ExampleSentencesRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Locale;
import java.util.ArrayList;

@ApplicationScoped
@RequiredArgsConstructor
public class ImportExampleService implements UseCase<ImportExampleDto, ExampleSentenceDto> {

  private final ExampleSentencesRepository exampleSentencesRepository;
  private final ExampleChecksumService checksumService;

  @Override
  @Transactional
  public ExampleSentenceDto handle(ImportExampleDto dto) {
    ImportExampleRequestDto request = dto.request();
    List<ExampleTranslationEntity> translations = translationsFrom(request.translations());
    ExampleSentenceEntity existing = exampleSentencesRepository.findByExternalId(dto.externalId()).orElse(null);
    List<String> linkedWordIds = request.linkedWordExternalIds() != null
      ? request.linkedWordExternalIds()
      : existing == null ? List.of() : existing.linkedWordExternalIds();

    ExampleSentenceEntity sentence = existing == null
      ? ExampleSentenceEntity.create(
        dto.externalId(), request.russian(), translations, request.contributor(), request.audioUrl(), linkedWordIds
      )
      : existing.replaceSourceData(
        request.russian(), translations, request.contributor(), request.audioUrl(), linkedWordIds
      );

    sentence = sentence.withChecksum(checksumService.calculate(sentence));
    return ExampleSentenceDto.fromEntity(exampleSentencesRepository.save(sentence));
  }

  private List<ExampleTranslationEntity> translationsFrom(List<ExampleTranslationInputDto> translations) {
    if (translations == null) {
      return List.of();
    }

    List<ExampleTranslationEntity> result = new ArrayList<>();
    for (int index = 0; index < translations.size(); index++) {
      ExampleTranslationInputDto translation = translations.get(index);
      if (translation.text() == null || translation.text().isBlank()) {
        continue;
      }
      if (translation.language() == null || translation.language().isBlank()) {
        throw new BadRequest("Example translation language is required");
      }
      result.add(new ExampleTranslationEntity(
        translation.language().toLowerCase(Locale.ROOT),
        translation.text(),
        translation.position() == null ? index : translation.position()
      ));
    }
    return List.copyOf(result);
  }

}
