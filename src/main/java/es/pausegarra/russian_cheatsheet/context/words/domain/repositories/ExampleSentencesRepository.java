package es.pausegarra.russian_cheatsheet.context.words.domain.repositories;

import es.pausegarra.russian_cheatsheet.common.domain.pagination_and_sorting.Paginated;
import es.pausegarra.russian_cheatsheet.context.words.domain.entities.ExampleSentenceEntity;

import java.util.Optional;
import java.util.UUID;

public interface ExampleSentencesRepository {

  Optional<ExampleSentenceEntity> findById(UUID id);

  ExampleSentenceEntity save(ExampleSentenceEntity sentence);

  Paginated<ExampleSentenceEntity> findAll(int page, int perPage);

  Paginated<ExampleSentenceEntity> findByWordId(UUID wordId, int page, int perPage);

}
