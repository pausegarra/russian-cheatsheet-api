package es.pausegarra.russkiy_po_moyemu.alphabet.infrastructure.requests;

public record CreateLetterRequest(
  String cyrillic, String ipa, String latin
) {}
