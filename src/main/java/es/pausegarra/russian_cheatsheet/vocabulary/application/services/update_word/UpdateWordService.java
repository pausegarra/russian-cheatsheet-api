package es.pausegarra.russian_cheatsheet.vocabulary.application.services.update_word;

import es.pausegarra.russian_cheatsheet.common.application.interfaces.Service;
import es.pausegarra.russian_cheatsheet.vocabulary.domain.entities.VerbConjugationEntity;
import es.pausegarra.russian_cheatsheet.vocabulary.domain.entities.WordCasesEntity;
import es.pausegarra.russian_cheatsheet.vocabulary.domain.entities.WordEntity;
import es.pausegarra.russian_cheatsheet.vocabulary.domain.enums.WordTypes;
import es.pausegarra.russian_cheatsheet.vocabulary.domain.exception.WordNotFound;
import es.pausegarra.russian_cheatsheet.vocabulary.domain.repositories.WordsRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@ApplicationScoped
@RequiredArgsConstructor
public class UpdateWordService implements Service<UpdateWordDto, Void> {

  private final WordsRepository wordsRepository;

  @Override
  @Transactional
  public Void handle(
    @Valid
    UpdateWordDto dto
  ) {
    WordEntity word = ensureEntityExists(dto.id());

    VerbConjugationEntity conjugations = resolveConjugations(word, dto);
    WordCasesEntity cases = resolveCases(word, dto);

    WordEntity updated = word.update(
      dto.russian(),
      dto.english(),
      dto.spanish(),
      dto.type(),
      conjugations,
      cases
    );

    wordsRepository.save(updated);

    return null;
  }

  private WordEntity ensureEntityExists(UUID id) {
    return wordsRepository.findById(id)
      .orElseThrow(() -> new WordNotFound(id.toString()));
  }

  private VerbConjugationEntity resolveConjugations(WordEntity word, UpdateWordDto dto) {
    if (dto.type() != WordTypes.VERB || dto.conjugations() == null) {
      return null;
    }

    VerbConjugationEntity existing = word.getConjugations();

    if (existing == null) {
      return dto.conjugations()
        .toEntity()
        .withWord(word);
    }

    return existing.update(dto.conjugations()
      .toEntity());
  }

  private WordCasesEntity resolveCases(WordEntity word, UpdateWordDto dto) {
    if (dto.type() == WordTypes.VERB || dto.cases() == null) {
      return null;
    }

    WordCasesEntity existing = word.getCases();
    if (existing == null) {
      return dto.cases()
        .toEntity()
        .withWord(word);
    }

    return existing.update(dto.cases()
      .toEntity());
  }

}
