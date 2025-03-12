package es.pausegarra.russkiy_po_moyemu.alphabet.infrastructure.projections;

import java.util.UUID;

public record LetterProjection(
    UUID id,
    String cyrillic,
    String latin,
    String ipa
) {
}
