package es.pausegarra.russkiy_po_moyemu.common.infrastructure.pagination;

import io.quarkus.hibernate.orm.panache.PanacheQuery;

public record PageInfo(
  int page, int pageSize, int totalPages, long totalElements, boolean hasNextPage, boolean hasPreviousPage
) {

  public static <T> PageInfo fromQuery(PanacheQuery<T> query) {
    return new PageInfo(
      query.page().index, query.page().size, query.pageCount(), query.count(), query.hasNextPage(),
      query.hasPreviousPage()
    );
  }

}
