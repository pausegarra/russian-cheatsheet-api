package es.pausegarra.russkiy_po_moyemu.alphabet.application.services.create_letter;

import es.pausegarra.russkiy_po_moyemu.alphabet.domain.entities.LetterEntity;
import es.pausegarra.russkiy_po_moyemu.alphabet.domain.repositories.LetterRepository;
import es.pausegarra.russkiy_po_moyemu.common.application.interfaces.Service;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@ApplicationScoped
@RequiredArgsConstructor
@Named("CreateLetterService")
public class CreateLetterService implements Service<CreateLetterCommand, UUID> {

  private final LetterRepository letterRepository;

  @Override
  @Transactional
  public UUID handle(@Valid CreateLetterCommand command) {
    LetterEntity letter = LetterEntity.create(
        null,
        command.cyrillic(),
        command.ipa(),
        command.latin()
    );

    letterRepository.save(letter);

    return letter.getId();
  }

}
