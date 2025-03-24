package es.pausegarra.russkiy_po_moyemu.vocabulary.infrastructure.requests;

public record CreateWordRequest(
    String russian,
    String english,
    String spanish,
    String type
) {
}
