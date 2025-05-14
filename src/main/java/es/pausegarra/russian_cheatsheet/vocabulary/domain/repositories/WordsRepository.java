package es.pausegarra.russian_cheatsheet.vocabulary.domain.repositories;

import es.pausegarra.russian_cheatsheet.common.domain.pagination_and_sorting.Paginated;
import es.pausegarra.russian_cheatsheet.vocabulary.domain.criterias.WordsSearchCriteria;
import es.pausegarra.russian_cheatsheet.vocabulary.domain.entities.WordEntity;

import java.util.Optional;
import java.util.UUID;

public interface WordsRepository {

  Paginated<WordEntity> findByCriteria(WordsSearchCriteria criteria);

  Optional<WordEntity> findById(UUID id);

  WordEntity save(WordEntity word);

  void delete(UUID id);

  Optional<WordEntity> findByRussian(String russian);

}
