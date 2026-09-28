package es.pausegarra.russian_cheatsheet.context.words.application.use_cases.find_word_relations;

import es.pausegarra.russian_cheatsheet.common.application.use_cases.UseCase;
import es.pausegarra.russian_cheatsheet.context.words.application.WordRelationsService;
import es.pausegarra.russian_cheatsheet.context.words.application.dto.WordRelationDto;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;

import java.util.List;

@ApplicationScoped
@RequiredArgsConstructor
public class FindWordRelationsService implements UseCase<FindWordRelationsDto, List<WordRelationDto>> {

  private final WordRelationsService relationsService;

  @Override
  public List<WordRelationDto> handle(FindWordRelationsDto dto) {
    return relationsService.findOutgoingDtos(dto.sourceWordId());
  }

}
