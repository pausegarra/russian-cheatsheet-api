package es.pausegarra.russian_cheatsheet.vocabulary.application.services.delete_word;

import es.pausegarra.russian_cheatsheet.common.application.interfaces.Service;
import es.pausegarra.russian_cheatsheet.vocabulary.domain.exception.WordNotFound;
import es.pausegarra.russian_cheatsheet.vocabulary.domain.repositories.WordsRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@ApplicationScoped
@RequiredArgsConstructor
public class DeleteWordService implements Service<DeleteWordDto, Void> {

  private final WordsRepository wordsRepository;

  @Override
  @Transactional
  public Void handle(DeleteWordDto dto) {
    ensureWordExists(dto.id());
    wordsRepository.delete(dto.id());

    return null;
  }

  private void ensureWordExists(UUID id) {
    wordsRepository.findById(id)
      .orElseThrow(() -> new WordNotFound(id.toString()));
  }

}
