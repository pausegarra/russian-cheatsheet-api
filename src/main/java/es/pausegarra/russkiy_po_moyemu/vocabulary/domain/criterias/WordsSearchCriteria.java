package es.pausegarra.russkiy_po_moyemu.vocabulary.domain.criterias;

import es.pausegarra.russkiy_po_moyemu.common.domain.criteria.Criteria;
import es.pausegarra.russkiy_po_moyemu.common.domain.pagination_and_sorting.Page;
import es.pausegarra.russkiy_po_moyemu.common.domain.pagination_and_sorting.Sort;
import es.pausegarra.russkiy_po_moyemu.common.domain.pagination_and_sorting.SortDirection;

public class WordsSearchCriteria extends Criteria {

  private WordsSearchCriteria(int page, int pageSize, String sortBy, String sortDirection) {
    super(
        new Page(page, pageSize),
        new Sort(sortBy, SortDirection.valueOf(sortDirection.toUpperCase()))
    );
  }

  public static WordsSearchCriteria create(int page, int pageSize, String sortBy, String sortDirection) {
    return new WordsSearchCriteria(page, pageSize, sortBy, sortDirection);
  }

}
