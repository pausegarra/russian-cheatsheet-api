package es.pausegarra.russian_cheatsheet.vocabulary.domain.criterias;

import es.pausegarra.russian_cheatsheet.common.domain.criteria.Criteria;
import es.pausegarra.russian_cheatsheet.common.domain.pagination_and_sorting.Page;
import es.pausegarra.russian_cheatsheet.common.domain.pagination_and_sorting.Sort;
import es.pausegarra.russian_cheatsheet.common.domain.pagination_and_sorting.SortDirection;
import lombok.Getter;

@Getter
public class WordsSearchCriteria extends Criteria {

  private final String search;

  private WordsSearchCriteria(
    int page,
    int pageSize,
    String sortBy,
    String sortDirection,
    String search
  ) {
    super(
      new Page(page, pageSize),
      new Sort(sortBy, SortDirection.valueOf(sortDirection.toUpperCase()))
    );
    this.search = search;
  }

  public static WordsSearchCriteria create(
    int page,
    int pageSize,
    String sortBy,
    String sortDirection,
    String search
  ) {
    return new WordsSearchCriteria(page, pageSize, sortBy, sortDirection, search);
  }

}
