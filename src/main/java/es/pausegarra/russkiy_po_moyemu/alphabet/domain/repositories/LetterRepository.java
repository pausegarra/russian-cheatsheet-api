package es.pausegarra.russkiy_po_moyemu.alphabet.domain.repositories;

import es.pausegarra.russkiy_po_moyemu.alphabet.domain.entities.LetterEntity;

public interface LetterRepository {

  void save (LetterEntity letter);

}
