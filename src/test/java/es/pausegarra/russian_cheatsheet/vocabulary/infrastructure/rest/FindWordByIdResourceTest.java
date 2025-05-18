package es.pausegarra.russian_cheatsheet.vocabulary.infrastructure.rest;

import es.pausegarra.russian_cheatsheet.common.application.interfaces.Service;
import es.pausegarra.russian_cheatsheet.mother.words.entities.WordEntityMother;
import es.pausegarra.russian_cheatsheet.vocabulary.application.dto.WordDto;
import es.pausegarra.russian_cheatsheet.vocabulary.application.services.find_by_id.FindWordByIdDto;
import es.pausegarra.russian_cheatsheet.vocabulary.domain.entities.WordEntity;
import es.pausegarra.russian_cheatsheet.vocabulary.infrastructure.presentations.WordPresentation;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.jboss.resteasy.reactive.RestResponse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@QuarkusTest
class FindWordByIdResourceTest {

  @InjectMock
  Service<FindWordByIdDto, WordDto> findWordByIdService;

  @Inject
  FindWordByIdResource findWordByIdResource;

  @Test
  public void shouldReturnWord() {
    WordEntity word = WordEntityMother.random()
      .build();

    when(findWordByIdService.handle(any(FindWordByIdDto.class))).thenReturn(WordDto.fromEntity(word));

    RestResponse<WordPresentation> response = findWordByIdResource.findWordById("a");

    assertEquals(200, response.getStatus());

    verify(findWordByIdService, times(1)).handle(any(FindWordByIdDto.class));
  }

}