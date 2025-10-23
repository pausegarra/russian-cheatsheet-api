package es.pausegarra.russian_cheatsheet.context.words.domain.repositories;

import es.pausegarra.russian_cheatsheet.context.words.domain.entities.WordEntity;

import java.util.Optional;

public interface WordsRepository {

  WordEntity save(WordEntity word);

  Optional<WordEntity> findById(String id);

}
