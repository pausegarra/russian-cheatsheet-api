package es.pausegarra.russkiy_po_moyemu.alphabet.infrastructure.requests;

import jakarta.validation.constraints.NotBlank;

public record CreateLetterRequest(
    @NotBlank String letter,
    @NotBlank String ipa
) {
}
