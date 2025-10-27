package es.pausegarra.russian_cheatsheet.context.words.infrastructure.rest;

import es.pausegarra.russian_cheatsheet.common.application.use_cases.UseCase;
import es.pausegarra.russian_cheatsheet.context.words.application.dto.requests.create_word.CreateWordDto;
import es.pausegarra.russian_cheatsheet.context.words.application.dto.responses.WordDto;
import es.pausegarra.russian_cheatsheet.context.words.application.use_cases.CreateWordUseCase;
import es.pausegarra.russian_cheatsheet.context.words.domain.enums.WordType;
import es.pausegarra.russian_cheatsheet.mother.WordMother;
import org.jboss.resteasy.reactive.RestResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateWordResourceTest {

  @Mock
  private UseCase<CreateWordDto, WordDto> createWordUseCase;

  @InjectMocks
  private CreateWordResource createWordResource;

  @Test
  public void shouldCreateWord() {
    WordDto wordDto = WordDto.fromEntity(WordMother.random().build());
    when(createWordUseCase.handle(any(CreateWordDto.class))).thenReturn(wordDto);

    CreateWordDto createWordDto = new CreateWordDto(
      "russian",
      "english",
      "spanish",
      WordType.OTHER,
      null,
      null,
      null
    );
    RestResponse<WordDto> restResponse = createWordResource.createWord(createWordDto);

    assertNotNull(restResponse);
    assertEquals(201, restResponse.getStatus());
    assertNotNull(restResponse.getEntity());

    verify(createWordUseCase).handle(any(CreateWordDto.class));
  }

}