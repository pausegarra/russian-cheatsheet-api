package es.pausegarra.russian_cheatsheet.context.words.application.use_cases.import_example;

import java.util.List;

public record ImportExampleRequestDto(
  String russian,
  List<ExampleTranslationInputDto> translations,
  String contributor,
  String audioUrl,
  List<String> linkedWordExternalIds
) {}
