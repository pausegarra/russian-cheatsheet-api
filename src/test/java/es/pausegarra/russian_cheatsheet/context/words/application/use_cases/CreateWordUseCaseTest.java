package es.pausegarra.russian_cheatsheet.context.words.application.use_cases;

import es.pausegarra.russian_cheatsheet.common.domain.exception.BadRequest;
import es.pausegarra.russian_cheatsheet.context.words.application.WordRelationsService;
import es.pausegarra.russian_cheatsheet.context.words.application.WordWriteMapper;
import es.pausegarra.russian_cheatsheet.context.words.application.dto.WordTranslationInputDto;
import es.pausegarra.russian_cheatsheet.context.words.application.use_cases.create_word.CreateWordDto;
import es.pausegarra.russian_cheatsheet.context.words.application.use_cases.create_word.CreateWordUseCase;
import es.pausegarra.russian_cheatsheet.context.words.domain.entities.WordEntity;
import es.pausegarra.russian_cheatsheet.context.words.domain.entities.WordFormsEntity;
import es.pausegarra.russian_cheatsheet.context.words.domain.enums.WordAspect;
import es.pausegarra.russian_cheatsheet.context.words.domain.enums.WordType;
import es.pausegarra.russian_cheatsheet.context.words.domain.repositories.WordsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateWordUseCaseTest {

  @Mock
  private WordsRepository wordsRepository;

  @Mock
  private WordRelationsService relationsService;

  @Spy
  private WordWriteMapper wordWriteMapper = new WordWriteMapper();

  @InjectMocks
  private CreateWordUseCase useCase;

  @Test
  void shouldCreateAWordWithSourceFormsAndRootAspect() {
    WordFormsEntity forms = WordFormsEntity.builder()
      .ru_verb_presfut_sg1("ввожу")
      .ru_verb_gerund_present("вводя")
      .build();
    CreateWordDto dto = new CreateWordDto(
      "вводить", null,
      List.of(new WordTranslationInputDto("es", "introducir", 0)),
      "Usage note", "https://example.org/audio.mp3", WordType.VERB, WordAspect.IMPERFECTIVE, forms
    );
    when(wordsRepository.create(any(WordEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));
    when(relationsService.findOutgoingWordDtos(any())).thenReturn(List.of());

    var created = useCase.handle(dto);

    assertEquals("вводить", created.russian());
    assertEquals(WordType.VERB, created.type());
    assertEquals(WordAspect.IMPERFECTIVE, created.aspect());
    assertEquals(forms, created.forms());
    assertEquals("Usage note", created.usage());
    assertEquals("https://example.org/audio.mp3", created.audioUrl());
    verify(wordsRepository).create(any(WordEntity.class));
  }

  @Test
  void shouldRejectFormsThatDoNotMatchTheWordType() {
    CreateWordDto dto = new CreateWordDto(
      "вводить", null, List.of(), null, null, WordType.VERB, WordAspect.PERFECTIVE,
      WordFormsEntity.builder().ru_noun_sg_nom("дом").build()
    );

    assertThrows(BadRequest.class, () -> useCase.handle(dto));
    verifyNoInteractions(wordsRepository);
  }

  @Test
  void shouldRejectAspectForANonVerb() {
    CreateWordDto dto = new CreateWordDto(
      "дом", null, List.of(), null, null, WordType.NOUN, WordAspect.PERFECTIVE, null
    );

    assertThrows(BadRequest.class, () -> useCase.handle(dto));
    verifyNoInteractions(wordsRepository);
  }
}
