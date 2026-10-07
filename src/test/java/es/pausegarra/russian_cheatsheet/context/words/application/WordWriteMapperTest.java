package es.pausegarra.russian_cheatsheet.context.words.application;

import es.pausegarra.russian_cheatsheet.common.domain.exception.BadRequest;
import es.pausegarra.russian_cheatsheet.context.words.application.dto.WordTranslationInputDto;
import es.pausegarra.russian_cheatsheet.context.words.application.use_cases.create_word.CreateWordDto;
import es.pausegarra.russian_cheatsheet.context.words.application.use_cases.update_word.UpdateWordDto;
import es.pausegarra.russian_cheatsheet.context.words.domain.entities.WordEntity;
import es.pausegarra.russian_cheatsheet.context.words.domain.enums.TranslationOrigin;
import es.pausegarra.russian_cheatsheet.context.words.domain.enums.WordType;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class WordWriteMapperTest {

  private final WordWriteMapper mapper = new WordWriteMapper();

  @Test
  void fromCreateNormalizesImportedTranslations() {
    WordEntity word = mapper.fromCreate(new CreateWordDto(
      "слово", "source-1", List.of(new WordTranslationInputDto("ES", "palabra", null)),
      null, null, WordType.NOUN, null, null
    ));

    assertEquals("es", word.translations().getFirst().language());
    assertEquals(TranslationOrigin.OPENRUSSIAN, word.translations().getFirst().managedBy());
  }

  @Test
  void fromUpdateRejectsImportedWordWithoutTranslations() {
    WordEntity existing = WordEntity.createImported(
      "source-1", "слово", List.of(), null, null, WordType.NOUN, null, null
    );
    UpdateWordDto request = new UpdateWordDto(
      existing.id(), "слово", "source-1", null, null, null, WordType.NOUN, null, null
    );

    assertThrows(BadRequest.class, () -> mapper.fromUpdate(existing, request));
  }
}
