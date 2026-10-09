package es.pausegarra.russian_cheatsheet.context.words.domain.repositories;

import es.pausegarra.russian_cheatsheet.common.domain.pagination_and_sorting.Paginated;
import es.pausegarra.russian_cheatsheet.context.words.domain.entities.ExampleSentenceEntity;
import es.pausegarra.russian_cheatsheet.context.words.domain.entities.ImportedExampleReferenceEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ExampleSentencesRepository {

  Optional<ExampleSentenceEntity> findById(UUID id);

  ExampleSentenceEntity save(ExampleSentenceEntity sentence);

  List<ExampleSentenceEntity> create(List<ExampleSentenceEntity> sentences);

  List<ExampleSentenceEntity> save(List<ExampleSentenceEntity> sentences);

  List<ExampleSentenceEntity> findAllByIds(List<UUID> ids);

  void deleteAllByIds(List<UUID> ids);

  Paginated<ExampleSentenceEntity> findAll(int page, int perPage);

  Paginated<ImportedExampleReferenceEntity> findImportIndex(int page, int perPage);

  Paginated<ExampleSentenceEntity> findByWordId(UUID wordId, int page, int perPage);

}
