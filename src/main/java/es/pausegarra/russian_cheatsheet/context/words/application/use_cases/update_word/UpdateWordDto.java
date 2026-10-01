package es.pausegarra.russian_cheatsheet.context.words.application.use_cases.update_word;

import es.pausegarra.russian_cheatsheet.context.words.application.dto.WordTranslationInputDto;
import es.pausegarra.russian_cheatsheet.context.words.domain.entities.WordFormsEntity;
import es.pausegarra.russian_cheatsheet.context.words.domain.enums.WordType;
import es.pausegarra.russian_cheatsheet.context.words.domain.enums.WordAspect;

import java.util.List;
import java.util.UUID;

public record UpdateWordDto(
  UUID id,
  String russian,
  String externalId,
  List<WordTranslationInputDto> translations,
  String usage,
  String audioUrl,
  WordType type,
  WordAspect aspect,
  WordFormsEntity forms
)
{}
