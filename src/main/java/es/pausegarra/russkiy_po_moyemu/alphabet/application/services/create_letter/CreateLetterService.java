package es.pausegarra.russkiy_po_moyemu.alphabet.application.services.create_letter;

import es.pausegarra.russkiy_po_moyemu.alphabet.domain.entities.LetterEntity;
import es.pausegarra.russkiy_po_moyemu.alphabet.domain.repositories.LetterRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@ApplicationScoped
@RequiredArgsConstructor
public class CreateLetterService {

  private final LetterRepository letterRepository;

  @Transactional
  public UUID handle(@Valid CreateLetterCommand command) {
    LetterEntity letter = LetterEntity.create(
        null,
        command.letter(),
        command.ipa()
    );

    letterRepository.save(letter);

    return letter.getId();
  }

}
