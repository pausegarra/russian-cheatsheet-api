package es.pausegarra.russkiy_po_moyemu.vocabulary.application.mappers;

import es.pausegarra.russkiy_po_moyemu.vocabulary.application.dto.WordDto;
import es.pausegarra.russkiy_po_moyemu.vocabulary.domain.entities.WordEntity;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class WordMapper {

  public WordDto fromEntityToDto(WordEntity entity) {
    return new WordDto(
        entity.getRussian(),
        entity.getEnglish(),
        entity.getSpanish(),
        entity.getType().toString()
    );
  }

}
