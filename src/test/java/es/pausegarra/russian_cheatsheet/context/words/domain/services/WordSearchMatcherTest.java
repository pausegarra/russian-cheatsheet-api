package es.pausegarra.russian_cheatsheet.context.words.domain.services;

import es.pausegarra.russian_cheatsheet.context.words.domain.entities.WordEntity;
import es.pausegarra.russian_cheatsheet.context.words.domain.entities.WordSearchResultEntity;
import es.pausegarra.russian_cheatsheet.context.words.domain.entities.WordTranslationEntity;
import es.pausegarra.russian_cheatsheet.context.words.domain.enums.TranslationOrigin;
import es.pausegarra.russian_cheatsheet.context.words.domain.enums.WordSearchMode;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class WordSearchMatcherTest {

  private final WordSearchMatcher matcher = new WordSearchMatcher();

  @Test
  void shouldMatchWholeRussianLemmaAndUsePrefixesOnlyInContainsMode() {
    assertEquals(
      "russian_exact",
      matcher.match(word("привет"), "привет", WordSearchMode.PRECISE).orElseThrow().match().kind()
    );
    assertTrue(matcher.match(word("приветик"), "привет", WordSearchMode.PRECISE).isEmpty());
    assertEquals(
      "russian_prefix",
      matcher.match(word("приветик"), "привет", WordSearchMode.CONTAINS).orElseThrow().match().kind()
    );
    assertEquals(
      "substring",
      matcher.match(word("неприветливый"), "привет", WordSearchMode.CONTAINS).orElseThrow().match().kind()
    );
  }

  @Test
  void shouldPreferWholeSpanishGlossToSubstringInsideEnglishWord() {
    WordEntity word = word(
      "привет",
      new WordTranslationEntity("en", "scholarship", TranslationOrigin.MANUAL, 0),
      new WordTranslationEntity("es", "¡salud! ¡hola! ¡ola!", TranslationOrigin.MANUAL, 1)
    );

    WordSearchResultEntity result = matcher.match(word, "hola", WordSearchMode.PRECISE).orElseThrow();

    assertEquals("translation_exact", result.match().kind());
    assertEquals("es", result.match().language());
    assertEquals("¡salud! ¡hola! ¡ola!", result.match().text());
  }

  @Test
  void shouldRankDirectGlossBeforeContextualMention() {
    WordEntity direct = word("здравствуй", new WordTranslationEntity("en", "hello", TranslationOrigin.MANUAL, 0));
    WordEntity contextual = word(
      "алло", new WordTranslationEntity("en", "greeting, saying hello", TranslationOrigin.MANUAL, 0)
    );

    assertEquals(
      "translation_exact",
      matcher.match(direct, "hello", WordSearchMode.PRECISE).orElseThrow().match().kind()
    );
    assertEquals(
      "translation_phrase",
      matcher.match(contextual, "hello", WordSearchMode.PRECISE).orElseThrow().match().kind()
    );
  }

  @Test
  void shouldKeepMultiwordMatchesTogetherInOneField() {
    WordEntity direct = word("доброе утро", new WordTranslationEntity("en", "good morning", TranslationOrigin.MANUAL, 0));
    WordEntity splitAcrossLanguages = word(
      "слово",
      new WordTranslationEntity("en", "good", TranslationOrigin.MANUAL, 0),
      new WordTranslationEntity("es", "morning", TranslationOrigin.MANUAL, 1)
    );
    WordEntity reversed = word("слово", new WordTranslationEntity("en", "morning good", TranslationOrigin.MANUAL, 0));

    assertEquals("translation_exact", matcher.match(direct, "good morning", WordSearchMode.PRECISE).orElseThrow().match().kind());
    assertTrue(matcher.match(splitAcrossLanguages, "good morning", WordSearchMode.PRECISE).isEmpty());
    assertTrue(matcher.match(reversed, "good morning", WordSearchMode.PRECISE).isEmpty());
  }

  @Test
  void shouldNormalizeRussianStressButPreserveYoAndSpanishEnye() {
    WordEntity stressed = word("ПРИВЕ́Т");
    WordEntity yo = word("ещё");
    WordEntity enye = word("palabra", new WordTranslationEntity("es", "año", TranslationOrigin.MANUAL, 0));

    assertEquals("russian_exact", matcher.match(stressed, "привет", WordSearchMode.PRECISE).orElseThrow().match().kind());
    assertTrue(matcher.match(yo, "еще", WordSearchMode.PRECISE).isEmpty());
    assertFalse(matcher.match(enye, "ano", WordSearchMode.PRECISE).isPresent());
    assertEquals("translation_exact", matcher.match(enye, "año", WordSearchMode.PRECISE).orElseThrow().match().kind());
  }

  private static WordEntity word(String russian, WordTranslationEntity... translations) {
    return WordEntity.builder()
      .id(UUID.randomUUID())
      .russian(russian)
      .translations(List.of(translations))
      .build();
  }
}
