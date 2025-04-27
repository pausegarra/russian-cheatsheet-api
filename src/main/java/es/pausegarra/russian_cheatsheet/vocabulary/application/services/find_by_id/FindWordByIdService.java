package es.pausegarra.russian_cheatsheet.vocabulary.application.services.find_by_id;

import es.pausegarra.russian_cheatsheet.common.application.interfaces.Service;
import es.pausegarra.russian_cheatsheet.vocabulary.application.dto.WordDto;
import es.pausegarra.russian_cheatsheet.vocabulary.domain.entities.WordEntity;
import es.pausegarra.russian_cheatsheet.vocabulary.domain.exception.WordNotFound;
import es.pausegarra.russian_cheatsheet.vocabulary.domain.repositories.WordsRepository;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
@ApplicationScoped
public class FindWordByIdService implements Service<FindWordByIdDto, WordDto> {

  private final WordsRepository wordsRepository;

  @Override
  public WordDto handle(FindWordByIdDto dto) {
    WordEntity word = wordsRepository.findById(UUID.fromString(dto.id()))
      .orElseThrow(() -> new WordNotFound(dto.id()));

    return WordDto.fromEntity(word);
  }

}

