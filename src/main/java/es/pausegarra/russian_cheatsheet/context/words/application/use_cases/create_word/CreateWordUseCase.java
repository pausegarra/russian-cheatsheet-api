package es.pausegarra.russian_cheatsheet.context.words.application.use_cases.create_word;

import es.pausegarra.russian_cheatsheet.common.application.use_cases.UseCase;
import es.pausegarra.russian_cheatsheet.common.domain.exception.BadRequest;
import es.pausegarra.russian_cheatsheet.context.words.application.WordRelationsService;
import es.pausegarra.russian_cheatsheet.context.words.application.dto.WordDto;
import es.pausegarra.russian_cheatsheet.context.words.application.dto.WordTranslationInputDto;
import es.pausegarra.russian_cheatsheet.context.words.domain.entities.WordEntity;
import es.pausegarra.russian_cheatsheet.context.words.domain.entities.WordFormsEntity;
import es.pausegarra.russian_cheatsheet.context.words.domain.entities.WordTranslationEntity;
import es.pausegarra.russian_cheatsheet.context.words.domain.enums.TranslationOrigin;
import es.pausegarra.russian_cheatsheet.context.words.domain.enums.WordAspect;
import es.pausegarra.russian_cheatsheet.context.words.domain.enums.WordType;
import es.pausegarra.russian_cheatsheet.context.words.domain.repositories.WordsRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@RequiredArgsConstructor
@ApplicationScoped
public class CreateWordUseCase implements UseCase<CreateWordDto, WordDto> {

  private final WordsRepository wordsRepository;
  private final WordRelationsService relationsService;

  @Transactional
  @Override
  public WordDto handle(CreateWordDto dto) {
    WordEntity created = wordsRepository.create(createWord(dto));
    if (created.externalId() != null) {
      relationsService.refreshChecksums(List.of(created.id()));
      created = wordsRepository.findById(created.id()).orElseThrow();
    }
    return WordDto.fromEntity(created, relationsService.findOutgoingWordDtos(created.id()));
  }

  private WordEntity createWord(CreateWordDto dto) {
    if (dto.externalId() != null) {
      if (dto.externalId().isBlank()) {
        throw new BadRequest("External ID is required for imported words");
      }
      if (dto.translations() == null) {
        throw new BadRequest("Imported word creation must include translations");
      }
      return WordEntity.createImported(
        dto.externalId(), dto.russian(), translationsFrom(dto.translations(), TranslationOrigin.OPENRUSSIAN),
        dto.usage(), dto.audioUrl(), dto.type(), dto.aspect(), validatedForms(dto.type(), dto.aspect(), dto.forms())
      );
    }

    return WordEntity.createManual(
      dto.russian(), translationsFrom(dto.translations(), TranslationOrigin.MANUAL), dto.type(), dto.aspect(),
      validatedForms(dto.type(), dto.aspect(), dto.forms())
    );
  }

  private WordFormsEntity validatedForms(WordType type, WordAspect aspect, WordFormsEntity forms) {
    if (type == null) {
      throw new BadRequest("Word type is required");
    }
    if (type == WordType.VERB && aspect == null) {
      throw new BadRequest("Verb words require an aspect");
    }
    if (type != WordType.VERB && aspect != null) {
      throw new BadRequest("Only verb words can have an aspect");
    }
    if (forms != null && !forms.isCompatibleWith(type)) {
      throw new BadRequest("Word forms do not match the word type");
    }
    return forms;
  }

  private List<WordTranslationEntity> translationsFrom(
    List<WordTranslationInputDto> translations,
    TranslationOrigin origin
  ) {
    if (translations == null) {
      return List.of();
    }
    List<WordTranslationEntity> result = new ArrayList<>();
    for (int index = 0; index < translations.size(); index++) {
      WordTranslationInputDto translation = translations.get(index);
      if (translation.text() == null || translation.text().isBlank()) {
        continue;
      }
      if (translation.language() == null || translation.language().isBlank()) {
        throw new BadRequest("Translation language is required");
      }
      result.add(new WordTranslationEntity(
        translation.language().toLowerCase(Locale.ROOT),
        translation.text(),
        origin,
        translation.position() == null ? index : translation.position()
      ));
    }
    return List.copyOf(result);
  }

}
