package es.pausegarra.russian_cheatsheet.context.words.application.use_cases.create_example;

import es.pausegarra.russian_cheatsheet.context.words.application.dto.ExampleTranslationInputDto;

import java.util.List;
import java.util.UUID;

public record CreateExampleDto(
  String externalId,
  String russian,
  List<ExampleTranslationInputDto> translations,
  String contributor,
  String audioUrl,
  List<UUID> linkedWordIds
) {}
