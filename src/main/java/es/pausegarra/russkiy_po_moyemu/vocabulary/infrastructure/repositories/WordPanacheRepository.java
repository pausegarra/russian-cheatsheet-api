package es.pausegarra.russkiy_po_moyemu.vocabulary.infrastructure.repositories;

import es.pausegarra.russkiy_po_moyemu.common.domain.pagination_and_sorting.Paginated;
import es.pausegarra.russkiy_po_moyemu.common.infrastructure.pagination.PageInfo;
import es.pausegarra.russkiy_po_moyemu.vocabulary.domain.criterias.WordsSearchCriteria;
import es.pausegarra.russkiy_po_moyemu.vocabulary.domain.entities.WordEntity;
import es.pausegarra.russkiy_po_moyemu.vocabulary.domain.repositories.WordsRepository;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.UUID;

@ApplicationScoped
public class WordPanacheRepository implements WordsRepository, PanacheRepository<WordEntity> {

  @Override
  public Paginated<WordEntity> findByCriteria(WordsSearchCriteria criteria) {
    Sort sort = Sort.by(
        criteria.getSorting().sortBy(),
        Sort.Direction.valueOf(criteria.getSorting().sortDirection().getValue())
    );
    Page page = Page.of(
        criteria.getPagination().page(),
        criteria.getPagination().pageSize()
    );
    PanacheQuery<WordEntity> query = findAll(sort)
        .page(page);

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

  @Override
  public void save(WordEntity word) {
    persist(word);
  }

}
