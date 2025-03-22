package es.pausegarra.russkiy_po_moyemu.vocabulary.application.services.find_paginated_words;

import io.smallrye.common.constraint.NotNull;

public record FindAllWordsPaginatedQuery(
    @NotNull int page,
    @NotNull int pageSize,
    @NotNull String sortBy,
    @NotNull String sortDirection
) {

  public static FindAllWordsPaginatedQuery from(int page, int pageSize, String sortBy, String sortDirection) {
    return new FindAllWordsPaginatedQuery(page, pageSize, sortBy, sortDirection);
  }

}
