package es.pausegarra.russkiy_po_moyemu.alphabet.application.services.list_letters;

import es.pausegarra.russkiy_po_moyemu.alphabet.application.dtos.LetterDto;
import es.pausegarra.russkiy_po_moyemu.alphabet.domain.entities.LetterEntity;
import es.pausegarra.russkiy_po_moyemu.alphabet.domain.repositories.LetterRepository;
import es.pausegarra.russkiy_po_moyemu.common.application.interfaces.Service;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;

@ApplicationScoped
@RequiredArgsConstructor
public class ListLettersService implements Service<ListLettersDto, List<LetterDto>> {

  private final LetterRepository letterRepository;

  @Transactional
  public List<LetterDto> handle(
    @Valid
    ListLettersDto query
  ) {
    List<LetterEntity> letters = letterRepository.fetchAll();

    return letters.stream().map(LetterDto::fromEntity).toList();
  }

}
