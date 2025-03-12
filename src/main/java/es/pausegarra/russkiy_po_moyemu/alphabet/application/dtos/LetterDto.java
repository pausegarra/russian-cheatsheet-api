package es.pausegarra.russkiy_po_moyemu.alphabet.application.dtos;

import java.time.Instant;
import java.util.UUID;

public record LetterDto(
    UUID id,
    String latin,
    String ipa,
    String cyrillic,
    Instant createdAt,
    Instant updatedAt
) {
}
