package es.pausegarra.russian_cheatsheet.context.words.application.use_cases.create_word;

import es.pausegarra.russian_cheatsheet.context.words.application.dto.WordTranslationInputDto;
import es.pausegarra.russian_cheatsheet.context.words.domain.entities.WordFormsEntity;
import es.pausegarra.russian_cheatsheet.context.words.domain.enums.WordAspect;
import es.pausegarra.russian_cheatsheet.context.words.domain.enums.WordType;

import java.util.List;

public record CreateWordDto(
  String russian,
  String externalId,
  List<WordTranslationInputDto> translations,
  String usage,
  String audioUrl,
  WordType type,
  WordAspect aspect,
  WordFormsEntity forms
) {}
