package es.pausegarra.russian_cheatsheet.context.words.application.use_cases.update_example;

import es.pausegarra.russian_cheatsheet.context.words.application.dto.ExampleTranslationInputDto;

import java.util.List;
import java.util.UUID;

public record UpdateExampleDto(
  UUID id,
  String russian,
  List<ExampleTranslationInputDto> translations,
  String contributor,
  String audioUrl,
  List<UUID> linkedWordIds
) {}
