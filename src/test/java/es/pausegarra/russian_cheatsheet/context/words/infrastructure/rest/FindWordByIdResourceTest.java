package es.pausegarra.russian_cheatsheet.context.words.infrastructure.rest;

import es.pausegarra.russian_cheatsheet.common.application.use_cases.UseCase;
import es.pausegarra.russian_cheatsheet.context.words.application.dto.WordDto;
import es.pausegarra.russian_cheatsheet.context.words.application.use_cases.find_word_by_id.FindWordByIdDto;
import es.pausegarra.russian_cheatsheet.context.words.domain.entities.WordEntity;
import es.pausegarra.russian_cheatsheet.mother.WordMother;
import org.jboss.resteasy.reactive.RestResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindWordByIdResourceTest {

  @Mock
  private UseCase<FindWordByIdDto, WordDto> service;

  @InjectMocks
  private FindWordByIdResource findWordByIdResource;

  @Test
  public void shouldFindWordById() {
    WordEntity word = WordMother.random().build();
    when(service.handle(any(FindWordByIdDto.class))).thenReturn(WordDto.fromEntity(word));

    RestResponse<WordDto> response = findWordByIdResource.findWordById(UUID.randomUUID().toString());

    assertNotNull(response);
    assertEquals(200, response.getStatus());
    assertEquals(word.id(), response.getEntity().id());

    verify(service).handle(any(FindWordByIdDto.class));
  }

}