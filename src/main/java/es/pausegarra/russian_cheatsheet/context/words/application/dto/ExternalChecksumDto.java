package es.pausegarra.russian_cheatsheet.context.words.application.dto;

import es.pausegarra.russian_cheatsheet.context.words.domain.entities.ExternalChecksumEntity;

public record ExternalChecksumDto(
  String externalId,
  String checksum
) {

  public static ExternalChecksumDto fromEntity(ExternalChecksumEntity entity) {
    return new ExternalChecksumDto(entity.externalId(), entity.checksum());
  }

}
