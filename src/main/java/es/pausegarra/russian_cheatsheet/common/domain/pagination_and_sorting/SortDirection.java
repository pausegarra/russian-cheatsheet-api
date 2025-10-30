package es.pausegarra.russian_cheatsheet.common.domain.pagination_and_sorting;

import lombok.Getter;

@Getter
public enum SortDirection {

  ASC("Ascending"), DESC("Descending");

  private final String value;

  SortDirection(String value) {
    this.value = value;
  }

}
