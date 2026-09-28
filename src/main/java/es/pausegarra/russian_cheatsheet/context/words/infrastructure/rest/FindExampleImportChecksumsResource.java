package es.pausegarra.russian_cheatsheet.context.words.infrastructure.rest;

import es.pausegarra.russian_cheatsheet.common.application.pagination.PaginatedDto;
import es.pausegarra.russian_cheatsheet.common.application.use_cases.UseCase;
import es.pausegarra.russian_cheatsheet.context.words.application.dto.ExternalChecksumDto;
import es.pausegarra.russian_cheatsheet.context.words.application.use_cases.find_import_checksums.FindExampleImportChecksumsDto;
import es.pausegarra.russian_cheatsheet.context.words.infrastructure.spec.FindExampleImportChecksumsApiSpec;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.jboss.resteasy.reactive.RestResponse;

@RequiredArgsConstructor
public class FindExampleImportChecksumsResource implements FindExampleImportChecksumsApiSpec {

  private final UseCase<FindExampleImportChecksumsDto, PaginatedDto<ExternalChecksumDto>> findChecksumsUseCase;

  @Override
  @RolesAllowed("words#create")
  public RestResponse<PaginatedDto<ExternalChecksumDto>> findExampleImportChecksums(int page, int perPage) {
    return RestResponse.ok(findChecksumsUseCase.handle(new FindExampleImportChecksumsDto(page, perPage)));
  }

}
