package es.pausegarra.russian_cheatsheet.context.words.application.use_cases.delete_words_batch;

import es.pausegarra.russian_cheatsheet.common.application.use_cases.UseCase;
import es.pausegarra.russian_cheatsheet.common.domain.exception.BadRequest;
import es.pausegarra.russian_cheatsheet.context.words.domain.repositories.WordsRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@ApplicationScoped
@RequiredArgsConstructor
public class DeleteWordsBatchService implements UseCase<List<UUID>, Void> {

  private final WordsRepository wordsRepository;

  @Override
  @Transactional
  public Void handle(List<UUID> ids) {
    validateIds(ids);
    wordsRepository.deleteAllByIds(ids);
    return null;
  }

  private void validateIds(List<UUID> ids) {
    if (ids == null || ids.isEmpty()) {
      throw new BadRequest("Batch must contain at least one item");
    }

    Set<UUID> uniqueIds = new HashSet<>();
    for (UUID id : ids) {
      if (id == null) {
        throw new BadRequest("Batch items cannot be null");
      }
      if (!uniqueIds.add(id)) {
        throw new BadRequest("Batch cannot contain duplicate word IDs");
      }
    }
  }
}
