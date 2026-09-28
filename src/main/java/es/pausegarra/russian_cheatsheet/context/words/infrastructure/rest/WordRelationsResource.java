package es.pausegarra.russian_cheatsheet.context.words.infrastructure.rest;

import es.pausegarra.russian_cheatsheet.common.application.use_cases.UseCase;
import es.pausegarra.russian_cheatsheet.context.words.application.dto.WordRelationDto;
import es.pausegarra.russian_cheatsheet.context.words.application.dto.WordRelationInputDto;
import es.pausegarra.russian_cheatsheet.context.words.application.use_cases.create_word_relation.CreateWordRelationDto;
import es.pausegarra.russian_cheatsheet.context.words.application.use_cases.create_word_relation.CreateWordRelationResultDto;
import es.pausegarra.russian_cheatsheet.context.words.application.use_cases.delete_word_relation.DeleteWordRelationDto;
import es.pausegarra.russian_cheatsheet.context.words.application.use_cases.find_word_relations.FindWordRelationsDto;
import es.pausegarra.russian_cheatsheet.context.words.infrastructure.spec.WordRelationsApiSpec;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.jboss.resteasy.reactive.RestResponse;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class WordRelationsResource implements WordRelationsApiSpec {

  private final UseCase<CreateWordRelationDto, CreateWordRelationResultDto> createWordRelationUseCase;
  private final UseCase<FindWordRelationsDto, List<WordRelationDto>> findWordRelationsUseCase;
  private final UseCase<DeleteWordRelationDto, Void> deleteWordRelationUseCase;

  @Override
  public RestResponse<List<WordRelationDto>> findWordRelations(UUID wordId) {
    List<WordRelationDto> relations = findWordRelationsUseCase.handle(new FindWordRelationsDto(wordId));
    return RestResponse.ok(relations);
  }

  @Override
  @RolesAllowed("words#create")
  public RestResponse<WordRelationDto> createWordRelation(UUID wordId, WordRelationInputDto request) {
    CreateWordRelationResultDto result = createWordRelationUseCase.handle(
      new CreateWordRelationDto(wordId, request)
    );
    return result.created()
      ? RestResponse.status(RestResponse.Status.CREATED, result.relation())
      : RestResponse.ok(result.relation());
  }

  @Override
  @RolesAllowed("words#delete")
  public RestResponse<Void> deleteWordRelation(UUID wordId, UUID relationId) {
    deleteWordRelationUseCase.handle(new DeleteWordRelationDto(wordId, relationId));
    return RestResponse.status(RestResponse.Status.NO_CONTENT);
  }

}
