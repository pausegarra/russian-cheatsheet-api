package es.pausegarra.russian_cheatsheet.common.application.pagination;

import es.pausegarra.russian_cheatsheet.common.domain.pagination_and_sorting.Paginated;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import java.util.List;

@Schema(
  name = "Paginated DTO"
)
public record PaginatedDto<E>(
  @Schema(
    description = "Data", examples = "[...]"
  ) List<E> data, @Schema(
  description = "Page", examples = "0"
) int page, @Schema(
  description = "Page Size", examples = "10"
) int pageSize, @Schema(
  description = "Total Pages", examples = "10"
) int totalPages, @Schema(
  description = "Total Elements", examples = "100"
) long totalElements, @Schema(
  description = "Has Next Page", examples = "true"
) boolean hasNextPage, @Schema(
  description = "Has Previous Page", examples = "false"
) boolean hasPreviousPage
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
