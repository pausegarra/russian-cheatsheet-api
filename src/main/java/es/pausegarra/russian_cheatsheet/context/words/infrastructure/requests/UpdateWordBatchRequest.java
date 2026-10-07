package es.pausegarra.russian_cheatsheet.context.words.infrastructure.requests;

import es.pausegarra.russian_cheatsheet.context.words.application.dto.WordTranslationInputDto;
import es.pausegarra.russian_cheatsheet.context.words.domain.entities.WordFormsEntity;
import es.pausegarra.russian_cheatsheet.context.words.domain.enums.WordAspect;
import es.pausegarra.russian_cheatsheet.context.words.domain.enums.WordType;

import java.util.List;
import java.util.UUID;

public record UpdateWordBatchRequest(
  UUID id,
  String russian,
  WordType type,
  WordAspect aspect,
  WordFormsEntity forms,
  String externalId,
  List<WordTranslationInputDto> translations,
  String usage,
  String audioUrl
) {}
