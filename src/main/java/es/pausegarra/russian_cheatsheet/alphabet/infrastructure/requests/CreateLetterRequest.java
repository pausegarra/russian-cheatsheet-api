package es.pausegarra.russian_cheatsheet.alphabet.infrastructure.requests;

public record CreateLetterRequest(
  String cyrillic, String ipa, String latin
) {}
