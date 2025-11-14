package es.pausegarra.russian_cheatsheet.context.words.domain.repositories;

import es.pausegarra.russian_cheatsheet.common.domain.pagination_and_sorting.Paginated;
import es.pausegarra.russian_cheatsheet.context.words.domain.criterias.WordSearchCriteria;
import es.pausegarra.russian_cheatsheet.context.words.domain.entities.WordEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WordsRepository {

  WordEntity create(WordEntity word);

  WordEntity save(WordEntity word);

  List<WordEntity> save(List<WordEntity> words);

  Optional<WordEntity> findById(UUID id);

  List<WordEntity> getAll();

  Paginated<WordEntity> findByCriteria(WordSearchCriteria criteria);

}
