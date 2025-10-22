package es.pausegarra.russian_cheatsheet.common.application.pagination;

import es.pausegarra.russian_cheatsheet.common.domain.pagination_and_sorting.Paginated;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class PaginationDtoTest {

  // Converting a Paginated domain object to a PaginatedDto with the same data
  @Test
  public void test_from_paginated_with_data() {
    // Arrange
    List<String> domainData = List.of("item1", "item2", "item3");
    Paginated<String> paginated = new Paginated<>(domainData, 2, 10, 5, 45L, true, true);
    List<String> dtoData = List.of("item1", "item2", "item3");

    // Act
    PaginatedDto<String> result = PaginatedDto.fromPaginated(paginated, dtoData);

    // Assert
    Assertions.assertEquals(dtoData, result.data());
    Assertions.assertEquals(2, result.page());
    Assertions.assertEquals(10, result.pageSize());
    Assertions.assertEquals(5, result.totalPages());
    Assertions.assertEquals(45L, result.totalElements());
    Assertions.assertTrue(result.hasNextPage());
    Assertions.assertTrue(result.hasPreviousPage());
  }

}
