package es.pausegarra.russkiy_po_moyemu.alphabet.application.mappers;

import es.pausegarra.russkiy_po_moyemu.alphabet.application.dtos.LetterDto;
import es.pausegarra.russkiy_po_moyemu.alphabet.domain.entities.LetterEntity;
import es.pausegarra.russkiy_po_moyemu.alphabet.infrastructure.projections.LetterProjection;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class LetterMapper {

  public LetterProjection fromDtoToProjection(LetterDto letterDto) {
    return new LetterProjection(
        letterDto.id(),
        letterDto.cyrillic(),
        letterDto.latin(),
        letterDto.ipa()
    );
  }

  public LetterDto fromEntityToDto(LetterEntity entity) {
    return new LetterDto(
        entity.getId(),
        entity.getCyrillic(),
        entity.getIpa(),
        entity.getLatin(),
        entity.getAuditFields().getCreatedAt(),
        entity.getAuditFields().getUpdatedAt()
    );
  }

}
