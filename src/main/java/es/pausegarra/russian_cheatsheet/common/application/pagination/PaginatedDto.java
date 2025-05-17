package es.pausegarra.russian_cheatsheet.common.application.pagination;

import es.pausegarra.russian_cheatsheet.common.domain.pagination_and_sorting.Paginated;

import java.util.List;

public record PaginatedDto<E>(
  List<E> data,
  int page,
  int pageSize,
  int totalPages,
  long totalElements,
  boolean hasNextPage,
  boolean hasPreviousPage
) {

  public static <T, E> PaginatedDto<E> fromPaginated(Paginated<T> paginated, List<E> data) {
    return new PaginatedDto<>(
      data,
      paginated.page(),
      paginated.pageSize(),
      paginated.totalPages(),
      paginated.totalElements(),
      paginated.hasNextPage(),
      paginated.hasPreviousPage()
    );
  }

}
