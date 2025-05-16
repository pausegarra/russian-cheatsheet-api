package es.pausegarra.russian_cheatsheet.vocabulary.application.services.update_word;

import es.pausegarra.russian_cheatsheet.common.application.interfaces.Service;
import es.pausegarra.russian_cheatsheet.vocabulary.domain.entities.VerbConjugationEntity;
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

    VerbConjugationEntity conjugations = word.getConjugations();
    if (dto.conjugations() != null && word.getType() == WordTypes.VERB) {
      conjugations = dto.conjugations()
        .toEntity()
        .withWord(word)
        .withId(word.getConjugations().getId());
    } else {
      conjugations = null;
    }

    WordEntity updated = word.update(
      dto.russian(),
      dto.english(),
      dto.spanish(),
      dto.type(),
      conjugations
    );

    wordsRepository.save(updated);

    return null;
  }

  private WordEntity ensureEntityExists(UUID id) {
    return wordsRepository.findById(id)
      .orElseThrow(() -> new WordNotFound(id.toString()));
  }

}
