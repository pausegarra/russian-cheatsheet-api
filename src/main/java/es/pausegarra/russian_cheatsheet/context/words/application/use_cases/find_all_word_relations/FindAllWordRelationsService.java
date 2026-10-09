package es.pausegarra.russian_cheatsheet.context.words.application.use_cases.find_all_word_relations;

import es.pausegarra.russian_cheatsheet.common.application.pagination.PaginatedDto;
import es.pausegarra.russian_cheatsheet.common.application.use_cases.UseCase;
import es.pausegarra.russian_cheatsheet.common.domain.exception.BadRequest;
import es.pausegarra.russian_cheatsheet.common.domain.pagination_and_sorting.Paginated;
import es.pausegarra.russian_cheatsheet.context.words.application.WordRelationsService;
import es.pausegarra.russian_cheatsheet.context.words.application.dto.RelatedWordDto;
import es.pausegarra.russian_cheatsheet.context.words.application.dto.WordDto;
import es.pausegarra.russian_cheatsheet.context.words.application.dto.WordRelationListItemDto;
import es.pausegarra.russian_cheatsheet.context.words.domain.entities.WordRelationDetailsEntity;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@ApplicationScoped
@RequiredArgsConstructor
public class FindAllWordRelationsService
  implements UseCase<FindAllWordRelationsDto, PaginatedDto<WordRelationListItemDto>> {

  private static final int MAX_PER_PAGE = 100;

  private final WordRelationsService relationsService;

  @Override
  public PaginatedDto<WordRelationListItemDto> handle(FindAllWordRelationsDto dto) {
    if (dto.page() < 0 || dto.perPage() < 1 || dto.perPage() > MAX_PER_PAGE) {
      throw new BadRequest("Page must be non-negative and perPage must be between 1 and " + MAX_PER_PAGE);
    }

    Paginated<WordRelationDetailsEntity> paginated = relationsService.findAll(dto.page(), dto.perPage());
    List<UUID> relatedWordIds = paginated.data().stream()
      .map(relation -> relation.relatedWord().id())
      .distinct()
      .toList();
    Map<UUID, List<RelatedWordDto>> outgoingRelations = relationsService.findOutgoingWordDtosByIds(relatedWordIds);

    List<WordRelationListItemDto> data = paginated.data().stream()
      .map(relation -> {
        UUID relatedWordId = relation.relatedWord().id();
        WordDto relatedWord = WordDto.fromEntity(
          relation.relatedWord(), outgoingRelations.getOrDefault(relatedWordId, List.of())
        );
        return new WordRelationListItemDto(
          relation.id(), relation.sourceWordId(), relation.sourceExternalId(), relatedWordId,
          relation.relatedWord().externalId(), relatedWord, relation.relation(), relation.checksum()
        );
      })
      .toList();

    return PaginatedDto.fromPaginated(paginated, data);
  }

}
