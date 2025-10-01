package es.pausegarra.template.context.auth.domain.dto;

import java.util.List;

public record PermissionDto(
  String rsname, String rsid, List<String> scopes
) {}
