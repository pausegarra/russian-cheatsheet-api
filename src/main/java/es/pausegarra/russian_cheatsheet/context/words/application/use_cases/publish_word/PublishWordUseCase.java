package es.pausegarra.russian_cheatsheet.context.words.application.use_cases.publish_word;

import es.pausegarra.russian_cheatsheet.common.application.use_cases.UseCase;
import es.pausegarra.russian_cheatsheet.context.words.domain.entities.WordEntity;
import es.pausegarra.russian_cheatsheet.context.words.domain.exception.WordNotFound;
import es.pausegarra.russian_cheatsheet.context.words.domain.repositories.WordsRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@ApplicationScoped
@RequiredArgsConstructor
public class PublishWordUseCase implements UseCase<PublishWordDto, Void> {

  private final WordsRepository wordsRepository;

  @Override
  @Transactional
  public Void handle(PublishWordDto dto) {
    WordEntity word = wordsRepository.findById(dto.id())
      .orElseThrow(() -> new WordNotFound(dto.id().toString()));

    wordsRepository.save(word.publish());

    return null;
  }

}
