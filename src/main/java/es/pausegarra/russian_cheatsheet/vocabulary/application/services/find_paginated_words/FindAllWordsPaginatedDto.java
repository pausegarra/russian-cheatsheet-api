package es.pausegarra.russian_cheatsheet.vocabulary.application.services.find_paginated_words;

import io.smallrye.common.constraint.NotNull;

public record FindAllWordsPaginatedDto(
  @NotNull int page, @NotNull int pageSize, @NotNull String sortBy, @NotNull String sortDirection, String search
) {

  public static FindAllWordsPaginatedDto from(int page, int pageSize, String sortBy, String sortDirection, String search) {
    return new FindAllWordsPaginatedDto(page, pageSize, sortBy, sortDirection, search);
  }

}
