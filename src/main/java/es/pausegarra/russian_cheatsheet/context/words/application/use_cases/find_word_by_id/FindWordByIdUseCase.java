package es.pausegarra.russian_cheatsheet.context.words.application.use_cases.find_word_by_id;

import es.pausegarra.russian_cheatsheet.common.application.use_cases.UseCase;
import es.pausegarra.russian_cheatsheet.context.words.application.dto.WordDto;
import es.pausegarra.russian_cheatsheet.context.words.domain.entities.WordEntity;
import es.pausegarra.russian_cheatsheet.context.words.domain.exception.WordNotFound;
import es.pausegarra.russian_cheatsheet.context.words.domain.repositories.WordsRepository;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;

@ApplicationScoped
@RequiredArgsConstructor
public class FindWordByIdUseCase implements UseCase<FindWordByIdDto, WordDto> {

  private final WordsRepository wordsRepository;

  @Override
  public WordDto handle(FindWordByIdDto dto) {
    WordEntity word = wordsRepository.findById(dto.id())
      .orElseThrow(() -> new WordNotFound(dto.id().toString()));

    return WordDto.fromEntity(word);
  }

}
