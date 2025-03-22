package es.pausegarra.russkiy_po_moyemu.common.domain.criteria;

import es.pausegarra.russkiy_po_moyemu.common.domain.pagination_and_sorting.Page;
import es.pausegarra.russkiy_po_moyemu.common.domain.pagination_and_sorting.Sort;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public abstract class Criteria {

  private final Page pagination;

  private final Sort sorting;

}
