package es.pausegarra.russian_cheatsheet.vocabulary.infrastructure.repositories;

import es.pausegarra.russian_cheatsheet.common.domain.pagination_and_sorting.Paginated;
import es.pausegarra.russian_cheatsheet.common.infrastructure.pagination.PageInfo;
import es.pausegarra.russian_cheatsheet.vocabulary.domain.criterias.WordsSearchCriteria;
import es.pausegarra.russian_cheatsheet.vocabulary.domain.entities.WordEntity;
import es.pausegarra.russian_cheatsheet.vocabulary.domain.repositories.WordsRepository;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class WordPanacheRepository implements WordsRepository, PanacheRepository<WordEntity> {

  @Override
  public Paginated<WordEntity> findByCriteria(WordsSearchCriteria criteria) {
    Sort sort = Sort.by(
      criteria.getSorting()
        .sortBy(), Sort.Direction.valueOf(criteria.getSorting()
                                            .sortDirection()
                                            .getValue())
    );
    Page page = Page.of(
      criteria.getPagination()
        .page(), criteria.getPagination()
        .pageSize()
    );
    PanacheQuery<WordEntity> query = findAll(sort).page(page);

    PageInfo pageInfo = PageInfo.fromQuery(query);

    return new Paginated<>(
      query.list(), pageInfo.page(), pageInfo.pageSize(), pageInfo.totalPages(), pageInfo.totalElements(),
      pageInfo.hasNextPage(), pageInfo.hasPreviousPage()
    );
  }

  @Override
  public Optional<WordEntity> findById(UUID id) {
    WordEntity word = this.find("id", id)
      .firstResult();

    if (word == null) {
      return Optional.empty();
    }

    return Optional.of(word);
  }

  @Override
  public WordEntity save(WordEntity word) {
    return getEntityManager().merge(word);
  }

  @Override
  public void delete(UUID id) {
    this.delete("id", id);
  }

}
