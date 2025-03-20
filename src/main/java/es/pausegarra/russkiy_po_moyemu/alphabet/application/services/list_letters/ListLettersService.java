package es.pausegarra.russkiy_po_moyemu.alphabet.application.services.list_letters;

import es.pausegarra.russkiy_po_moyemu.alphabet.application.dtos.LetterDto;
import es.pausegarra.russkiy_po_moyemu.alphabet.application.mappers.LetterMapper;
import es.pausegarra.russkiy_po_moyemu.alphabet.domain.entities.LetterEntity;
import es.pausegarra.russkiy_po_moyemu.alphabet.domain.repositories.LetterRepository;
import es.pausegarra.russkiy_po_moyemu.common.application.interfaces.Service;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;

@ApplicationScoped
@RequiredArgsConstructor
public class ListLettersService implements Service<ListLettersQuery, List<LetterDto>> {

  private final LetterRepository letterRepository;

  private final LetterMapper letterMapper;

  public List<LetterDto> handle(@Valid ListLettersQuery query) {
    List<LetterEntity> letters = letterRepository.fetchAll();

    return letters.stream()
        .map(letterMapper::fromEntityToDto)
        .toList();
  }

}
