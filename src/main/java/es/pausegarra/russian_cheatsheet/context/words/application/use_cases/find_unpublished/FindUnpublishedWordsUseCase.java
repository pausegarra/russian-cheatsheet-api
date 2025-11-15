package es.pausegarra.russian_cheatsheet.context.words.application.use_cases.find_unpublished;

import es.pausegarra.russian_cheatsheet.common.application.pagination.PaginatedDto;
import es.pausegarra.russian_cheatsheet.common.application.use_cases.UseCase;
import es.pausegarra.russian_cheatsheet.common.domain.pagination_and_sorting.Paginated;
import es.pausegarra.russian_cheatsheet.context.words.application.dto.WordDto;
import es.pausegarra.russian_cheatsheet.context.words.domain.criterias.WordSearchCriteria;
import es.pausegarra.russian_cheatsheet.context.words.domain.entities.WordEntity;
import es.pausegarra.russian_cheatsheet.context.words.domain.repositories.WordsRepository;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@ApplicationScoped
public class FindUnpublishedWordsUseCase implements UseCase<FindUnpublishedWordsDto, PaginatedDto<WordDto>> {

  private final WordsRepository wordsRepository;

  @Override
  public PaginatedDto<WordDto> handle(FindUnpublishedWordsDto dto) {
    WordSearchCriteria criteria = WordSearchCriteria.create(
      dto.page(),
      dto.perPage(),
      dto.sortBy(),
      dto.sortDirection(),
      dto.search(),
      false
    );
    Paginated<WordEntity> paginated = wordsRepository.findByCriteria(criteria);
    List<WordDto> dtos = paginated.data().stream().map(WordDto::fromEntity).toList();

    return PaginatedDto.fromPaginated(paginated, dtos);
  }

}
