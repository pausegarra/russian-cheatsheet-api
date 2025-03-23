package es.pausegarra.russkiy_po_moyemu.vocabulary.domain.repositories;

import es.pausegarra.russkiy_po_moyemu.common.domain.pagination_and_sorting.Paginated;
import es.pausegarra.russkiy_po_moyemu.vocabulary.domain.criterias.WordsSearchCriteria;
import es.pausegarra.russkiy_po_moyemu.vocabulary.domain.entities.WordEntity;

public interface WordsRepository {

  Paginated<WordEntity> findByCriteria(WordsSearchCriteria criteria);

}
