package es.pausegarra.template.common.domain.pagination_and_sorting;

public record PageInfo(
  int page,
  int pageSize,
  int totalPages,
  long totalElements,
  boolean hasNextPage,
  boolean hasPreviousPage
) {}
