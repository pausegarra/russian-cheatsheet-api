package es.pausegarra.russian_cheatsheet.context.words.application.use_cases.find_import_checksums;

import es.pausegarra.russian_cheatsheet.common.application.pagination.PaginatedDto;
import es.pausegarra.russian_cheatsheet.common.application.use_cases.UseCase;
import es.pausegarra.russian_cheatsheet.common.domain.pagination_and_sorting.Paginated;
import es.pausegarra.russian_cheatsheet.context.words.application.dto.ExternalChecksumDto;
import es.pausegarra.russian_cheatsheet.context.words.domain.entities.ExternalChecksumEntity;
import es.pausegarra.russian_cheatsheet.context.words.domain.repositories.ExampleSentencesRepository;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;

@ApplicationScoped
@RequiredArgsConstructor
public class FindExampleImportChecksumsService implements UseCase<FindExampleImportChecksumsDto, PaginatedDto<ExternalChecksumDto>> {

  private final ExampleSentencesRepository exampleSentencesRepository;

  @Override
  public PaginatedDto<ExternalChecksumDto> handle(FindExampleImportChecksumsDto dto) {
    Paginated<ExternalChecksumEntity> paginated = exampleSentencesRepository.findImportChecksums(dto.page(), dto.perPage());
    return PaginatedDto.fromPaginated(
      paginated,
      paginated.data().stream().map(ExternalChecksumDto::fromEntity).toList()
    );
  }

}
