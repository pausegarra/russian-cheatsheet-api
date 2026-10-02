package es.pausegarra.russian_cheatsheet.context.words.application.use_cases.delete_word_relation;

import es.pausegarra.russian_cheatsheet.common.application.use_cases.UseCase;
import es.pausegarra.russian_cheatsheet.context.words.application.WordRelationsService;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;

@ApplicationScoped
@RequiredArgsConstructor
public class DeleteWordRelationService implements UseCase<DeleteWordRelationDto, Void> {

  private final WordRelationsService relationsService;

  @Override
  public Void handle(DeleteWordRelationDto dto) {
    relationsService.deleteOutgoing(dto.sourceWordId(), dto.relationId());
    return null;
  }

}
