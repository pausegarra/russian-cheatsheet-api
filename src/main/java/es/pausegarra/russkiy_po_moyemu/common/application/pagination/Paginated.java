package es.pausegarra.russkiy_po_moyemu.common.application.pagination;

import java.util.List;

public record Paginated<E>(
    List<E> data,
    int page,
    int pageSize,
    int totalPages,
    long totalElements,
    boolean hasNextPage,
    boolean hasPreviousPage
) {
}
