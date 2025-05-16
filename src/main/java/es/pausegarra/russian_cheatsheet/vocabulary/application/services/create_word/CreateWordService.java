package es.pausegarra.russian_cheatsheet.vocabulary.application.services.create_word;

import es.pausegarra.russian_cheatsheet.common.application.interfaces.Service;
import es.pausegarra.russian_cheatsheet.vocabulary.domain.entities.VerbConjugationEntity;
import es.pausegarra.russian_cheatsheet.vocabulary.domain.entities.WordEntity;
import es.pausegarra.russian_cheatsheet.vocabulary.domain.enums.WordTypes;
import es.pausegarra.russian_cheatsheet.vocabulary.domain.exception.WordAlreadyExists;
import es.pausegarra.russian_cheatsheet.vocabulary.domain.repositories.WordsRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
@RequiredArgsConstructor
public class CreateWordService implements Service<CreateWordDto, UUID> {

  private final WordsRepository wordsRepository;

  @Override
  @Transactional
  public UUID handle(
    @Valid
    CreateWordDto dto
  ) {
    ensureWordDoesNotExist(dto.russian());
    WordEntity word = WordEntity.create(
      null,
      dto.russian(),
      dto.english(),
      dto.spanish(),
      dto.type()
    );

    WordEntity savedWord = wordsRepository.save(word);

    if (word.getType() == WordTypes.VERB && dto.conjugations() != null) {
      VerbConjugationEntity conjugations = dto.conjugations()
        .toEntity()
        .withWord(savedWord);
      savedWord = savedWord.withConjugations(conjugations);
    }

    WordEntity saved = wordsRepository.save(savedWord);

    return saved.getId();
  }

  private void ensureWordDoesNotExist(String russian) {
    Optional<WordEntity> word = wordsRepository.findByRussian(russian);
    if (word.isPresent()) {
      throw new WordAlreadyExists(russian);
    }
  }

}
