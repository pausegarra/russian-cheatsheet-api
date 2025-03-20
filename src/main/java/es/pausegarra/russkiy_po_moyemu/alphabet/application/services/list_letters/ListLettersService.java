package es.pausegarra.russkiy_po_moyemu.alphabet.application.services.list_letters;

import es.pausegarra.russkiy_po_moyemu.alphabet.application.dtos.LetterDto;
import es.pausegarra.russkiy_po_moyemu.alphabet.application.mappers.LetterMapper;
import es.pausegarra.russkiy_po_moyemu.alphabet.domain.entities.LetterEntity;
import es.pausegarra.russkiy_po_moyemu.alphabet.domain.repositories.LetterRepository;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;

import java.util.List;

@ApplicationScoped
@RequiredArgsConstructor
public class ListLettersService {

  private final LetterRepository letterRepository;

  private final LetterMapper letterMapper;

  public List<LetterDto> handle() {
    List<LetterEntity> letters = letterRepository.fetchAll();

    return letters.stream()
        .map(letterMapper::fromEntityToDto)
        .toList();
  }

}
