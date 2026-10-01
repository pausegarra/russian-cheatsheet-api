package es.pausegarra.russian_cheatsheet.context.words.application.use_cases.import_example;

public record ImportExampleDto(
  String externalId,
  ImportExampleRequestDto request
) {}
