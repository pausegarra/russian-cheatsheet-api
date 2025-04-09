package es.pausegarra.russkiy_po_moyemu.auth.infrastructure.dto;

import java.util.List;

public record PermissionDto(
  String rsname, String rsid, List<String> scopes
) {
}
