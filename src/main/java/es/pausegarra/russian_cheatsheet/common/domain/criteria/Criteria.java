package es.pausegarra.russian_cheatsheet.common.domain.criteria;

import es.pausegarra.russian_cheatsheet.common.domain.pagination_and_sorting.Page;
import es.pausegarra.russian_cheatsheet.common.domain.pagination_and_sorting.Sort;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public abstract class Criteria {

  private final Page pagination;

  private final Sort sorting;

}
