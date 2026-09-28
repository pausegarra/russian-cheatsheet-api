package es.pausegarra.russian_cheatsheet.context.words.application.use_cases.delete_word;

import es.pausegarra.russian_cheatsheet.common.application.use_cases.UseCase;
import es.pausegarra.russian_cheatsheet.context.words.application.WordRelationsService;
import es.pausegarra.russian_cheatsheet.context.words.domain.entities.WordEntity;
import es.pausegarra.russian_cheatsheet.context.words.domain.exception.WordNotFound;
import es.pausegarra.russian_cheatsheet.context.words.domain.repositories.WordRelationsRepository;
import es.pausegarra.russian_cheatsheet.context.words.domain.repositories.WordsRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@ApplicationScoped
@RequiredArgsConstructor
public class DeleteWordUseCase implements UseCase<DeleteWordDto, Void> {

  private final WordsRepository wordsRepository;
  private final WordRelationsRepository relationsRepository;
  private final WordRelationsService relationsService;

  @Override
  @Transactional
  public Void handle(DeleteWordDto dto) {
    WordEntity word = wordsRepository.findById(dto.id())
      .orElseThrow(() -> new WordNotFound(dto.id().toString()));

    var changedSourceIds = relationsRepository.findIncomingSourceIds(word.id());
    wordsRepository.delete(word);
    relationsService.refreshChecksums(changedSourceIds);

    return null;
  }

}
