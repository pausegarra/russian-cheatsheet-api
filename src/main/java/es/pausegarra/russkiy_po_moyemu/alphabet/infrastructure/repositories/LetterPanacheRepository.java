package es.pausegarra.russkiy_po_moyemu.alphabet.infrastructure.repositories;

import es.pausegarra.russkiy_po_moyemu.alphabet.domain.entities.LetterEntity;
import es.pausegarra.russkiy_po_moyemu.alphabet.domain.repositories.LetterRepository;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class LetterPanacheRepository implements LetterRepository, PanacheRepository<LetterEntity> {

  public void save(LetterEntity letter) {
    persist(letter);
  }

}
