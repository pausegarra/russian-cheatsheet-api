package es.pausegarra.russian_cheatsheet.context.words.application.use_cases.update_word;

import es.pausegarra.russian_cheatsheet.common.domain.exception.BadRequest;
import es.pausegarra.russian_cheatsheet.context.words.application.WordRelationsService;
import es.pausegarra.russian_cheatsheet.context.words.application.dto.WordTranslationInputDto;
import es.pausegarra.russian_cheatsheet.context.words.domain.entities.WordEntity;
import es.pausegarra.russian_cheatsheet.context.words.domain.entities.WordFormsEntity;
import es.pausegarra.russian_cheatsheet.context.words.domain.entities.WordTranslationEntity;
import es.pausegarra.russian_cheatsheet.context.words.domain.enums.TranslationOrigin;
import es.pausegarra.russian_cheatsheet.context.words.domain.enums.WordAspect;
import es.pausegarra.russian_cheatsheet.context.words.domain.enums.WordType;
import es.pausegarra.russian_cheatsheet.context.words.domain.repositories.WordsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateWordUseCaseTest {

  @Mock
  private WordsRepository wordsRepository;

  @Mock
  private WordRelationsService relationsService;

  @InjectMocks
  private UpdateWordUseCase useCase;

  @Test
  void shouldUpdateAspectAndUnifiedForms() {
    UUID id = UUID.randomUUID();
    WordEntity existing = WordEntity.builder()
      .id(id)
      .russian("дом")
      .type(WordType.NOUN)
      .translations(List.of(new WordTranslationEntity("es", "casa", TranslationOrigin.MANUAL, 0)))
      .forms(WordFormsEntity.builder().ru_noun_sg_nom("дом").build())
      .build();
    WordFormsEntity newForms = WordFormsEntity.builder()
      .ru_verb_presfut_sg1("иду")
      .ru_verb_gerund_present("идя")
      .build();
    UpdateWordDto dto = new UpdateWordDto(
      id, "идти", null,
      List.of(new WordTranslationInputDto("es", "ir", 0)),
      null, null, WordType.VERB, WordAspect.IMPERFECTIVE, newForms
    );
    when(wordsRepository.findById(id)).thenReturn(Optional.of(existing));
    when(wordsRepository.save(any(WordEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));
    when(relationsService.findOutgoingWordDtos(id)).thenReturn(List.of());

    var updated = useCase.handle(dto);

    assertEquals(WordType.VERB, updated.type());
    assertEquals(WordAspect.IMPERFECTIVE, updated.aspect());
    assertEquals(newForms, updated.forms());
    verify(wordsRepository).save(any(WordEntity.class));
  }

  @Test
  void shouldRejectFormsThatDoNotMatchTheUpdatedType() {
    UUID id = UUID.randomUUID();
    WordEntity existing = WordEntity.builder().id(id).russian("дом").type(WordType.NOUN).build();
    UpdateWordDto dto = new UpdateWordDto(
      id, "идти", null, List.of(), null, null, WordType.VERB, WordAspect.PERFECTIVE,
      WordFormsEntity.builder().ru_noun_sg_nom("дом").build()
    );
    when(wordsRepository.findById(id)).thenReturn(Optional.of(existing));

    assertThrows(BadRequest.class, () -> useCase.handle(dto));
    verify(wordsRepository, never()).save(any(WordEntity.class));
  }
}
