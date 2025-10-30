package es.pausegarra.russian_cheatsheet.context.words.domain.repositories;

import es.pausegarra.russian_cheatsheet.common.domain.pagination_and_sorting.Paginated;
import es.pausegarra.russian_cheatsheet.context.words.domain.criterias.WordSearchCriteria;
import es.pausegarra.russian_cheatsheet.context.words.domain.entities.WordEntity;

import java.util.Optional;

public interface WordsRepository {

  WordEntity create(WordEntity word);

  WordEntity save(WordEntity word);

  Optional<WordEntity> findById(String id);

  Paginated<WordEntity> findByCriteria(WordSearchCriteria criteria);

}
