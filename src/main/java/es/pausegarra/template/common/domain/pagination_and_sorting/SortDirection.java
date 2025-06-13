package es.pausegarra.template.common.domain.pagination_and_sorting;

import lombok.Getter;

@Getter
public enum SortDirection {

  ASC("Ascending"), DESC("Descending");

  private String value;

  SortDirection(String value) {
    this.value = value;
  }

}
