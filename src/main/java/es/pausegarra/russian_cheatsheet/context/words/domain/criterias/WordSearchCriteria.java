package es.pausegarra.russian_cheatsheet.context.words.domain.criterias;

import es.pausegarra.russian_cheatsheet.common.domain.criteria.Criteria;
import es.pausegarra.russian_cheatsheet.common.domain.pagination_and_sorting.Page;
import es.pausegarra.russian_cheatsheet.common.domain.pagination_and_sorting.Sort;
import es.pausegarra.russian_cheatsheet.common.domain.pagination_and_sorting.SortDirection;
import lombok.Getter;

@Getter
public class WordSearchCriteria extends Criteria {

  private final String search;

  private final boolean published;

  private WordSearchCriteria(int page, int perPage, String sortBy, String sortDirection, String search, boolean published) {
    super(new Page(page, perPage), new Sort(sortBy, SortDirection.valueOf(sortDirection)));
    this.search = search;
    this.published = published;
  }

  public static WordSearchCriteria create(int page, int perPage, String sortBy, String sortDirection, String search) {
    return new WordSearchCriteria(page, perPage, sortBy, sortDirection, search, true);
  }

  public static WordSearchCriteria create(int page, int perPage, String sortBy, String sortDirection, String search, boolean published) {
    return new WordSearchCriteria(page, perPage, sortBy, sortDirection, search, published);
  }

}
