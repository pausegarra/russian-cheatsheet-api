package es.pausegarra.russian_cheatsheet.context.words.infrastructure.rest;

import es.pausegarra.russian_cheatsheet.common.application.use_cases.UseCase;
import es.pausegarra.russian_cheatsheet.context.words.application.use_cases.delete_word.DeleteWordDto;
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
class DeleteWordResourceTest {

  @Mock
  private UseCase<DeleteWordDto, Void> deleteWordUseCase;

  @InjectMocks
  private DeleteWordResource deleteWordResource;

  @Test
  public void shouldReturnNoContent() {
    when(deleteWordUseCase.handle(any(DeleteWordDto.class))).thenReturn(null);

    var response = deleteWordResource.deleteWord(UUID.randomUUID().toString());

    assertNotNull(response);
    assertEquals(204, response.getStatus());
    verify(deleteWordUseCase).handle(any(DeleteWordDto.class));
  }

}
