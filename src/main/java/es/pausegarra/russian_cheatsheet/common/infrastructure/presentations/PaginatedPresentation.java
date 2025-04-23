package es.pausegarra.russian_cheatsheet.common.infrastructure.presentations;

import es.pausegarra.russian_cheatsheet.common.application.pagination.PaginatedDto;

import java.util.List;

public record PaginatedPresentation<T>(
  List<T> data, int page, int pageSize, int totalPages, long totalElements, boolean hasNextPage, boolean hasPreviousPage
) {

  public static <T, R> PaginatedPresentation<R> fromDto(PaginatedDto<T> dto, List<R> data) {
    return new PaginatedPresentation<>(
      data, dto.page(), dto.pageSize(), dto.totalPages(), dto.totalElements(), dto.hasNextPage(),
      dto.hasPreviousPage()
    );
  }

}
