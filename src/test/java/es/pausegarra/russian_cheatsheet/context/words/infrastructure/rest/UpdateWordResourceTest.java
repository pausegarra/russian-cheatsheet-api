package es.pausegarra.russian_cheatsheet.context.words.infrastructure.rest;

import es.pausegarra.russian_cheatsheet.common.application.use_cases.UseCase;
import es.pausegarra.russian_cheatsheet.context.words.application.dto.WordDto;
import es.pausegarra.russian_cheatsheet.context.words.application.use_cases.update_word.UpdateWordDto;
import es.pausegarra.russian_cheatsheet.context.words.domain.entities.WordEntity;
import es.pausegarra.russian_cheatsheet.context.words.infrastructure.requests.UpdateWordRequest;
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
class UpdateWordResourceTest {

  @Mock
  private UseCase<UpdateWordDto, WordDto> updateWordUseCase;

  @InjectMocks
  private UpdateWordResource updateWordResource;

  @Test
  void shouldUpdateWord() {
    WordEntity word = WordMother.random().build();
    when(updateWordUseCase.handle(any(UpdateWordDto.class))).thenReturn(WordDto.fromEntity(word));

    UpdateWordRequest request = new UpdateWordRequest("russian", "spanish", "english", "OTHER", null, null, null);
    RestResponse<WordDto> response = updateWordResource.updateWord(UUID.randomUUID().toString(), request);

    assertNotNull(response);
    assertEquals(200, response.getStatus());

    verify(updateWordUseCase).handle(any(UpdateWordDto.class));
  }

}