package es.pausegarra.russian_cheatsheet.context.words.application.dto.responses;

import es.pausegarra.russian_cheatsheet.context.words.application.dto.WordDto;
import es.pausegarra.russian_cheatsheet.context.words.application.dto.WordTranslationDto;
import es.pausegarra.russian_cheatsheet.context.words.domain.entities.WordEntity;
import es.pausegarra.russian_cheatsheet.context.words.domain.entities.WordFormsEntity;
import es.pausegarra.russian_cheatsheet.context.words.domain.enums.WordAspect;
import es.pausegarra.russian_cheatsheet.context.words.domain.enums.WordType;
import es.pausegarra.russian_cheatsheet.mother.WordMother;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class WordDtoTest {

  @Test
  void shouldMapWordIncludingRootAspectAndSourceForms() {
    WordFormsEntity forms = WordFormsEntity.builder()
      .ru_verb_presfut_sg1("ввожу")
      .ru_verb_gerund_present("вводя")
      .ru_verb_participle_active_past("вводивший")
      .build();
    WordEntity word = WordMother.random()
      .type(WordType.VERB)
      .aspect(WordAspect.IMPERFECTIVE)
      .forms(forms)
      .build();

    WordDto dto = WordDto.fromEntity(word);

    assertNotNull(dto);
    assertEquals(word.id(), dto.id());
    assertEquals(word.russian(), dto.russian());
    assertEquals(word.translations().stream().map(WordTranslationDto::fromEntity).toList(), dto.translations());
    assertEquals(WordType.VERB, dto.type());
    assertEquals(WordAspect.IMPERFECTIVE, dto.aspect());
    assertEquals(forms, dto.forms());
  }
}
