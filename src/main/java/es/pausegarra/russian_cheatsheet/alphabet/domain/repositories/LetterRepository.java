package es.pausegarra.russian_cheatsheet.alphabet.domain.repositories;

import es.pausegarra.russian_cheatsheet.alphabet.domain.entities.LetterEntity;

import java.util.List;

public interface LetterRepository {

  void save(LetterEntity letter);

  List<LetterEntity> fetchAll();

}
