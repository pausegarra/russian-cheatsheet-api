package es.pausegarra.russian_cheatsheet.context.words.application.use_cases.update_word;

import es.pausegarra.russian_cheatsheet.common.application.use_cases.UseCase;
import es.pausegarra.russian_cheatsheet.common.domain.exception.BadRequest;
import es.pausegarra.russian_cheatsheet.context.words.application.WordChecksumService;
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
import es.pausegarra.russian_cheatsheet.context.words.domain.exception.WordNotFound;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@ApplicationScoped
@RequiredArgsConstructor
public class UpdateWordUseCase implements UseCase<UpdateWordDto, WordDto> {

  private final WordsRepository wordsRepository;
  private final WordRelationsService relationsService;
  private final WordChecksumService checksumService;

  @Override
  @Transactional
  public WordDto handle(UpdateWordDto dto) {
    WordEntity word = wordsRepository.findById(dto.id())
      .orElseThrow(() -> new WordNotFound(dto.id().toString()));
    boolean importedWord = dto.externalId() != null || word.externalId() != null;
    WordFormsEntity forms = validatedForms(dto.type(), dto.aspect(), dto.forms(), importedWord);

    WordEntity updated;
    if (dto.externalId() != null) {
      if (word.externalId() == null || !word.externalId().equals(dto.externalId())) {
        throw new BadRequest("External ID does not match the word being updated");
      }
      if (dto.translations() == null) {
        throw new BadRequest("Imported word updates must include translations");
      }
      updated = word.replaceImportedData(
        dto.externalId(), dto.russian(), translationsFrom(dto.translations(), TranslationOrigin.OPENRUSSIAN),
        dto.usage(), dto.audioUrl(), dto.type(), dto.aspect(), forms
      );
    } else if (dto.translations() != null) {
      updated = word.replaceManualTranslations(
        dto.russian(), translationsFrom(dto.translations(), TranslationOrigin.MANUAL), dto.type(), dto.aspect(), forms
      );
    } else {
      updated = word.updateWordDetails(dto.russian(), dto.type(), dto.aspect(), forms);
    }

    if (updated.forms() != null && !updated.forms().isCompatibleWith(updated.type())) {
      throw new BadRequest("Word forms do not match the word type");
    }

    if (updated.externalId() != null) {
      updated = updated.withChecksum(checksumService.calculate(updated));
    }
    WordEntity saved = wordsRepository.save(updated);
    return WordDto.fromEntity(saved, relationsService.findOutgoingWordDtos(saved.id()));
  }

  private WordFormsEntity validatedForms(
    WordType type,
    WordAspect aspect,
    WordFormsEntity forms,
    boolean importedWord
  ) {
    if (type == null) {
      if (!importedWord) {
        throw new BadRequest("Word type is required");
      }
      if (aspect != null || (forms != null && !forms.hasNoForms())) {
        throw new BadRequest("Words without a type cannot include aspect or forms");
      }
      return null;
    }
    if (type == WordType.VERB && aspect == null && !importedWord) {
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
