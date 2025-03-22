package es.pausegarra.russkiy_po_moyemu.vocabulary.infrastructure.repositories;

import es.pausegarra.russkiy_po_moyemu.common.application.pagination.Paginated;
import es.pausegarra.russkiy_po_moyemu.common.infrastructure.pagination.PageInfo;
import es.pausegarra.russkiy_po_moyemu.vocabulary.domain.criterias.WordsSearchCriteria;
import es.pausegarra.russkiy_po_moyemu.vocabulary.domain.entities.WordEntity;
import es.pausegarra.russkiy_po_moyemu.vocabulary.domain.repositories.WordsRepository;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class WordPanacheRepository implements WordsRepository, PanacheRepository<WordEntity> {

  @Override
  public Paginated<WordEntity> findByCriteria(WordsSearchCriteria criteria) {
    Sort sort = Sort.by(
        criteria.getSorting().sortBy(),
        Sort.Direction.valueOf(criteria.getSorting().sortDirection().getValue())
    );
    PanacheQuery<WordEntity> query = findAll(sort)
        .page(criteria.getPagination());

    PageInfo pageInfo = PageInfo.fromQuery(query);

    return new Paginated<>(
        query.list(),
        pageInfo.page(),
        pageInfo.pageSize(),
        pageInfo.totalPages(),
        pageInfo.totalElements(),
        pageInfo.hasNextPage(),
        pageInfo.hasPreviousPage()
    );
  }

}
