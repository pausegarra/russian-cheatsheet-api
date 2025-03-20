package es.pausegarra.russkiy_po_moyemu.alphabet.domain.repositories;

import es.pausegarra.russkiy_po_moyemu.alphabet.domain.entities.LetterEntity;

import java.util.List;

public interface LetterRepository {

  void save (LetterEntity letter);

  List<LetterEntity> fetchAll();

}
