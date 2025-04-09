package es.pausegarra.russkiy_po_moyemu.vocabulary.application.services.create_word;

import es.pausegarra.russkiy_po_moyemu.common.application.interfaces.Service;
import es.pausegarra.russkiy_po_moyemu.vocabulary.domain.entities.WordEntity;
import es.pausegarra.russkiy_po_moyemu.vocabulary.domain.repositories.WordsRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@ApplicationScoped
@RequiredArgsConstructor
public class CreateWordService implements Service<CreateWordDto, UUID> {

  private final WordsRepository wordsRepository;

  @Override
  @Transactional
  public UUID handle(
    @Valid
    CreateWordDto dto
  ) {
    WordEntity word = WordEntity.create(null, dto.russian(), dto.english(), dto.spanish(), dto.type());

    WordEntity saved = wordsRepository.save(word);

    return saved.getId();
  }

}
