package es.pausegarra.russian_cheatsheet.context.words.infrastructure.repositories;

import es.pausegarra.russian_cheatsheet.common.domain.pagination_and_sorting.PageInfo;
import es.pausegarra.russian_cheatsheet.common.domain.pagination_and_sorting.Paginated;
import es.pausegarra.russian_cheatsheet.context.words.domain.criterias.WordSearchCriteria;
import es.pausegarra.russian_cheatsheet.context.words.domain.entities.WordEntity;
import es.pausegarra.russian_cheatsheet.context.words.domain.repositories.WordsRepository;
import es.pausegarra.russian_cheatsheet.context.words.infrastructure.models.WordModel;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@ApplicationScoped
public class WordsPanacheRepository implements WordsRepository, PanacheRepository<WordModel> {

  @Override
  public WordEntity create(WordEntity word) {
    WordModel wordModel = WordModel.fromEntity(word);

    persist(wordModel);

    return wordModel.toEntity();
  }

  @Override
  public WordEntity save(WordEntity word) {
    WordModel wordModel = WordModel.fromEntity(word);

    WordModel saved = getEntityManager().merge(wordModel);

    return saved.toEntity();
  }

  @Override
  public List<WordEntity> save(List<WordEntity> words) {
    List<WordModel> models = words.stream().map(WordModel::fromEntity).toList();

    List<WordModel> saved = models.stream().map(getEntityManager()::merge).toList();

    return saved.stream().map(WordModel::toEntity).toList();
  }

  @Override
  public Optional<WordEntity> findById(String id) {
    return find("id", id).firstResultOptional().map(WordModel::toEntity);
  }

  @Override
  public List<WordEntity> getAll() {
    return findAll().stream().map(WordModel::toEntity).toList();
  }

  @Override
  public Paginated<WordEntity> findByCriteria(WordSearchCriteria criteria) {
    Page page = Page.of(criteria.getPagination().page(), criteria.getPagination().pageSize());
    Sort sort = Sort.by(criteria.getSorting().sortBy(), Sort.Direction.valueOf(criteria.getSorting().sortDirection().getValue()));

    PanacheQuery<WordModel> query = find(
      "lower(russian) like ?1 or lower(spanish) like ?1 or lower(english) like ?1",
      sort,
      "%" + criteria.getSearch().toLowerCase() + "%"
    ).page(page);

    PageInfo pageInfo = PageInfo.fromQuery(query);

    return new Paginated<>(
      query.list().stream().map(WordModel::toEntity).toList(),
      pageInfo.page(),
      pageInfo.pageSize(),
      pageInfo.totalPages(),
      pageInfo.totalElements(),
      pageInfo.hasNextPage(),
      pageInfo.hasPreviousPage()
    );
  }

}
