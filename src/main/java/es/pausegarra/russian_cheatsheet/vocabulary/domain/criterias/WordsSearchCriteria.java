package es.pausegarra.russian_cheatsheet.vocabulary.domain.criterias;

import es.pausegarra.russian_cheatsheet.common.domain.criteria.Criteria;
import es.pausegarra.russian_cheatsheet.common.domain.pagination_and_sorting.Page;
import es.pausegarra.russian_cheatsheet.common.domain.pagination_and_sorting.Sort;
import es.pausegarra.russian_cheatsheet.common.domain.pagination_and_sorting.SortDirection;

public class WordsSearchCriteria extends Criteria {

  private WordsSearchCriteria(int page, int pageSize, String sortBy, String sortDirection) {
    super(new Page(page, pageSize), new Sort(sortBy, SortDirection.valueOf(sortDirection.toUpperCase())));
  }

  public static WordsSearchCriteria create(int page, int pageSize, String sortBy, String sortDirection) {
    return new WordsSearchCriteria(page, pageSize, sortBy, sortDirection);
  }

}
