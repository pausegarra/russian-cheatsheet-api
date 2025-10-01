package es.pausegarra.template.common.domain.pagination_and_sorting;

import lombok.experimental.UtilityClass;

@UtilityClass
public class PageUtils {

  public PageInfo from(int page, int pageSize, long total, int offset, int limit) {
    int totalPages = (int) Math.ceil((double) (total / limit));
    boolean hasNextPage = offset + limit < total;
    boolean hasPreviousPage = offset > 0;

    return new PageInfo(page, pageSize, totalPages, total, hasNextPage, hasPreviousPage);
  }

}
