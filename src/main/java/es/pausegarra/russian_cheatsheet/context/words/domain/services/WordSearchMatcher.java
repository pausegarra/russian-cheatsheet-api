package es.pausegarra.russian_cheatsheet.context.words.domain.services;

import es.pausegarra.russian_cheatsheet.context.words.domain.entities.WordEntity;
import es.pausegarra.russian_cheatsheet.context.words.domain.entities.WordSearchResultEntity;
import es.pausegarra.russian_cheatsheet.context.words.domain.entities.WordTranslationEntity;
import es.pausegarra.russian_cheatsheet.context.words.domain.enums.WordSearchMode;

import java.text.Normalizer;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.regex.Pattern;

public class WordSearchMatcher {

  private static final Pattern WHITESPACE = Pattern.compile("\\s+", Pattern.UNICODE_CHARACTER_CLASS);
  private static final Pattern GLOSS_SEPARATOR = Pattern.compile("[,;!¡?¿]+");
  private static final Pattern PARENTHETICAL_NOTE = Pattern.compile("\\([^)]*\\)");

  public Optional<WordSearchResultEntity> match(WordEntity word, String search, WordSearchMode mode) {
    String query = normalize(search);
    if (query.isEmpty() || query.codePoints().noneMatch(Character::isLetterOrDigit)) {
      return Optional.empty();
    }

    MatchCandidate best = matchRussian(word.russian(), query, mode);
    List<WordTranslationEntity> translations = word.translations() == null ? List.of() : word.translations();
    for (WordTranslationEntity translation : translations) {
      if (!"en".equals(translation.language()) && !"es".equals(translation.language())) {
        continue;
      }
      MatchCandidate candidate = matchTranslation(translation, query, mode);
      if (candidate != null && precedes(candidate, best)) {
        best = candidate;
      }
    }

    if (best == null) {
      return Optional.empty();
    }

    return Optional.of(new WordSearchResultEntity(word, best.match()));
  }

  public static String normalize(String value) {
    if (value == null) {
      return "";
    }
    String withoutStress = value.replace("\u0301", "").toLowerCase(Locale.ROOT);
    return WHITESPACE.matcher(Normalizer.normalize(withoutStress, Normalizer.Form.NFC))
      .replaceAll(" ")
      .strip();
  }

  private static MatchCandidate matchRussian(String russian, String query, WordSearchMode mode) {
    String normalized = normalize(russian);
    if (normalized.equals(query)) {
      return candidate(1, "russian_exact", "ru", russian, 0, 0);
    }
    if (containsWholePhrase(normalized, query)) {
      return candidate(3, "russian_phrase", "ru", russian, 0, 0);
    }
    if (mode == WordSearchMode.CONTAINS && normalized.startsWith(query)) {
      return candidate(5, "russian_prefix", "ru", russian, 0, 0);
    }
    if (mode == WordSearchMode.CONTAINS && normalized.contains(query)) {
      return candidate(6, "substring", "ru", russian, 0, 0);
    }
    return null;
  }

  private static MatchCandidate matchTranslation(WordTranslationEntity translation, String query, WordSearchMode mode) {
    String normalized = normalize(translation.text());
    int languageRank = "en".equals(translation.language()) ? 1 : 2;
    if (hasExactGloss(normalized, query)) {
      return candidate(2, "translation_exact", translation.language(), translation.text(), languageRank, translation.position());
    }
    if (containsWholePhrase(normalized, query)) {
      return candidate(4, "translation_phrase", translation.language(), translation.text(), languageRank, translation.position());
    }
    if (mode == WordSearchMode.CONTAINS && normalized.contains(query)) {
      return candidate(6, "substring", translation.language(), translation.text(), languageRank, translation.position());
    }
    return null;
  }

  private static boolean hasExactGloss(String translation, String query) {
    for (String gloss : GLOSS_SEPARATOR.split(translation)) {
      String withoutNote = PARENTHETICAL_NOTE.matcher(gloss).replaceAll("");
      if (normalize(withoutNote).equals(query)) {
        return true;
      }
    }
    return false;
  }

  private static boolean containsWholePhrase(String value, String query) {
    int start = value.indexOf(query);
    while (start >= 0) {
      int end = start + query.length();
      boolean leftBoundary = start == 0 || !isWordCharacter(value.codePointBefore(start));
      boolean rightBoundary = end == value.length() || !isWordCharacter(value.codePointAt(end));
      if (leftBoundary && rightBoundary) {
        return true;
      }
      start = value.indexOf(query, start + Character.charCount(value.codePointAt(start)));
    }
    return false;
  }

  private static boolean isWordCharacter(int codePoint) {
    int type = Character.getType(codePoint);
    return Character.isLetterOrDigit(codePoint)
      || type == Character.NON_SPACING_MARK
      || type == Character.COMBINING_SPACING_MARK
      || type == Character.ENCLOSING_MARK;
  }

  private static MatchCandidate candidate(int rank, String kind, String language, String text, int languageRank, int position) {
    return new MatchCandidate(rank, languageRank, position, text, new WordSearchResultEntity.Match(kind, language, text));
  }

  private static boolean precedes(MatchCandidate candidate, MatchCandidate current) {
    if (current == null) {
      return true;
    }
    int comparison = Integer.compare(candidate.rank(), current.rank());
    if (comparison == 0) comparison = Integer.compare(candidate.languageRank(), current.languageRank());
    if (comparison == 0) comparison = Integer.compare(candidate.position(), current.position());
    if (comparison == 0) comparison = candidate.text().compareTo(current.text());
    return comparison < 0;
  }

  private record MatchCandidate(int rank, int languageRank, int position, String text, WordSearchResultEntity.Match match) {}
}
