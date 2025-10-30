package es.pausegarra.russian_cheatsheet.context.words.application.use_cases.find_words_by_criteria;

import es.pausegarra.russian_cheatsheet.common.application.pagination.PaginatedDto;
import es.pausegarra.russian_cheatsheet.common.application.use_cases.UseCase;
import es.pausegarra.russian_cheatsheet.common.domain.pagination_and_sorting.Paginated;
import es.pausegarra.russian_cheatsheet.context.words.application.dto.requests.find_words_by_criteria.FindWordsByCriteriaDto;
import es.pausegarra.russian_cheatsheet.context.words.application.dto.responses.WordDto;
import es.pausegarra.russian_cheatsheet.context.words.domain.criterias.WordSearchCriteria;
import es.pausegarra.russian_cheatsheet.context.words.domain.entities.WordEntity;
import es.pausegarra.russian_cheatsheet.context.words.domain.repositories.WordsRepository;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;

@ApplicationScoped
@RequiredArgsConstructor
public class FindWordsByCriteriaUseCase implements UseCase<FindWordsByCriteriaDto, PaginatedDto<WordDto>> {

  private final WordsRepository wordsRepository;

  @Override
  public PaginatedDto<WordDto> handle(FindWordsByCriteriaDto dto) {
    WordSearchCriteria criteria = WordSearchCriteria.create(
      dto.page(),
      dto.perPage(),
      dto.sortBy(),
      dto.sortDirection(),
      dto.search()
    );
    Paginated<WordEntity> paginated = wordsRepository.findByCriteria(criteria);

    return PaginatedDto.fromPaginated(paginated, paginated.data().stream().map(WordDto::fromEntity).toList());
  }

}
