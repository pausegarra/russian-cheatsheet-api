package es.pausegarra.russian_cheatsheet.context.words.application.use_cases.create_word_relations_batch;

import es.pausegarra.russian_cheatsheet.common.application.use_cases.UseCase;
import es.pausegarra.russian_cheatsheet.common.domain.exception.BadRequest;
import es.pausegarra.russian_cheatsheet.context.words.application.WordRelationsService;
import es.pausegarra.russian_cheatsheet.context.words.application.dto.WordRelationBatchInputDto;
import es.pausegarra.russian_cheatsheet.context.words.application.dto.WordRelationBatchResultDto;
import es.pausegarra.russian_cheatsheet.context.words.domain.entities.WordRelationEntity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@ApplicationScoped
@RequiredArgsConstructor
public class CreateWordRelationsBatchService
  implements UseCase<List<WordRelationBatchInputDto>, List<WordRelationBatchResultDto>> {

  private final WordRelationsService relationsService;

  @Override
  @Transactional
  public List<WordRelationBatchResultDto> handle(List<WordRelationBatchInputDto> relations) {
    validateBatch(relations);
    return relationsService.createOutgoingBatch(relations);
  }

  private void validateBatch(List<WordRelationBatchInputDto> relations) {
    if (relations == null || relations.isEmpty()) {
      throw new BadRequest("Batch must contain at least one item");
    }

    Set<WordRelationEntity> uniqueRelations = new HashSet<>();
    for (WordRelationBatchInputDto relation : relations) {
      if (relation == null) {
        throw new BadRequest("Batch items cannot be null");
      }
      if (relation.wordId() == null || relation.relatedWordId() == null || relation.relation() == null) {
        throw new BadRequest("Every relation must include wordId, relatedWordId, and relation");
      }
      if (!uniqueRelations.add(new WordRelationEntity(
        relation.wordId(), relation.relatedWordId(), relation.relation()
      ))) {
        throw new BadRequest("Batch cannot contain duplicate word relations");
      }
    }
  }

}
