package es.pausegarra.russian_cheatsheet.context.words.application.use_cases.find_examples;

import es.pausegarra.russian_cheatsheet.common.application.pagination.PaginatedDto;
import es.pausegarra.russian_cheatsheet.common.application.use_cases.UseCase;
import es.pausegarra.russian_cheatsheet.common.domain.pagination_and_sorting.Paginated;
import es.pausegarra.russian_cheatsheet.context.words.application.dto.ExampleSentenceDto;
import es.pausegarra.russian_cheatsheet.context.words.domain.entities.ExampleSentenceEntity;
import es.pausegarra.russian_cheatsheet.context.words.domain.exception.WordNotFound;
import es.pausegarra.russian_cheatsheet.context.words.domain.repositories.ExampleSentencesRepository;
import es.pausegarra.russian_cheatsheet.context.words.domain.repositories.WordsRepository;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;

@ApplicationScoped
@RequiredArgsConstructor
public class FindExamplesService implements UseCase<FindExamplesDto, PaginatedDto<ExampleSentenceDto>> {

  private final ExampleSentencesRepository exampleSentencesRepository;
  private final WordsRepository wordsRepository;

  @Override
  public PaginatedDto<ExampleSentenceDto> handle(FindExamplesDto dto) {
    if (dto.wordId() != null && wordsRepository.findById(dto.wordId()).isEmpty()) {
      throw new WordNotFound(dto.wordId().toString());
    }

    Paginated<ExampleSentenceEntity> paginated = dto.wordId() == null
      ? exampleSentencesRepository.findAll(dto.page(), dto.perPage())
      : exampleSentencesRepository.findByWordId(dto.wordId(), dto.page(), dto.perPage());

    return PaginatedDto.fromPaginated(
      paginated,
      paginated.data().stream().map(ExampleSentenceDto::fromEntity).toList()
    );
  }

}
