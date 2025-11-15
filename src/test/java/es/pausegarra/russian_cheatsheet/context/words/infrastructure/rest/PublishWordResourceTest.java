package es.pausegarra.russian_cheatsheet.context.words.infrastructure.rest;

import es.pausegarra.russian_cheatsheet.common.application.use_cases.UseCase;
import es.pausegarra.russian_cheatsheet.context.words.application.use_cases.publish_word.PublishWordDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PublishWordResourceTest {

  @Mock
  private UseCase<PublishWordDto, Void> publishWordUseCase;

  @InjectMocks
  private PublishWordResource publishWordResource;

  @Test
  public void shouldReturnNoContent() {
    when(publishWordUseCase.handle(any(PublishWordDto.class))).thenReturn(null);

    publishWordResource.publishWord(UUID.randomUUID().toString());

    verify(publishWordUseCase).handle(any(PublishWordDto.class));
  }

}