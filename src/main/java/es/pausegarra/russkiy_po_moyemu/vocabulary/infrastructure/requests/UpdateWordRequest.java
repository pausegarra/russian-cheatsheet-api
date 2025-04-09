package es.pausegarra.russkiy_po_moyemu.vocabulary.infrastructure.requests;

public record UpdateWordRequest(
  String russian, String english, String spanish, String type
) {
}
