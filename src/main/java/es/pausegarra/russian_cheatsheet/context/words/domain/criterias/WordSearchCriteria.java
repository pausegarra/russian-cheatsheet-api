package es.pausegarra.russian_cheatsheet.context.words.domain.criterias;

import es.pausegarra.russian_cheatsheet.common.domain.criteria.Criteria;
import es.pausegarra.russian_cheatsheet.common.domain.pagination_and_sorting.Page;
import es.pausegarra.russian_cheatsheet.common.domain.pagination_and_sorting.Sort;
import es.pausegarra.russian_cheatsheet.common.domain.pagination_and_sorting.SortDirection;
import es.pausegarra.russian_cheatsheet.context.words.domain.enums.WordSearchMode;
import lombok.Getter;

@Getter
public class WordSearchCriteria extends Criteria {

  private final String search;

  private final boolean published;

  private final WordSearchMode mode;

  private WordSearchCriteria(
    int page,
    int perPage,
    String sortBy,
    String sortDirection,
    String search,
    boolean published,
    WordSearchMode mode
  ) {
    super(new Page(page, perPage), new Sort(sortBy, SortDirection.valueOf(sortDirection)));
    this.search = search;
    this.published = published;
    this.mode = mode;
  }

  public static WordSearchCriteria create(int page, int perPage, String sortBy, String sortDirection, String search) {
    return create(page, perPage, sortBy, sortDirection, search, null, true);
  }

  public static WordSearchCriteria create(
    int page,
    int perPage,
    String sortBy,
    String sortDirection,
    String search,
    String mode
  ) {
    return create(page, perPage, sortBy, sortDirection, search, mode, true);
  }

  public static WordSearchCriteria create(int page, int perPage, String sortBy, String sortDirection, String search, boolean published) {
    return create(page, perPage, sortBy, sortDirection, search, null, published);
  }

  public static WordSearchCriteria create(
    int page,
    int perPage,
    String sortBy,
    String sortDirection,
    String search,
    String mode,
    boolean published
  ) {
    return new WordSearchCriteria(page, perPage, sortBy, sortDirection, search, published, WordSearchMode.fromQueryParameter(mode));
  }

}
