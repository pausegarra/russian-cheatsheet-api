package es.pausegarra.russian_cheatsheet.context.words.application.use_cases.create_word_relation;

import es.pausegarra.russian_cheatsheet.common.application.use_cases.UseCase;
import es.pausegarra.russian_cheatsheet.context.words.application.WordRelationsService;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;

@ApplicationScoped
@RequiredArgsConstructor
public class CreateWordRelationService implements UseCase<CreateWordRelationDto, CreateWordRelationResultDto> {

  private final WordRelationsService relationsService;

  @Override
  public CreateWordRelationResultDto handle(CreateWordRelationDto dto) {
    return relationsService.createOutgoingAndRefresh(dto.sourceWordId(), dto.relation());
  }

}
