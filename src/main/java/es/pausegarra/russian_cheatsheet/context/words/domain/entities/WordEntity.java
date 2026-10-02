package es.pausegarra.russian_cheatsheet.context.words.domain.entities;

import es.pausegarra.russian_cheatsheet.context.words.domain.enums.TranslationOrigin;
import es.pausegarra.russian_cheatsheet.context.words.domain.enums.WordAspect;
import es.pausegarra.russian_cheatsheet.context.words.domain.enums.WordType;
import lombok.Builder;
import lombok.With;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Builder
public record WordEntity(
  UUID id,
  String externalId,
  String russian,
  List<WordTranslationEntity> translations,
  String usage,
  String audioUrl,
  String checksum,
  WordType type,
  WordAspect aspect,
  WordFormsEntity forms,
  @With Instant publishedAt,
  String createdBy,
  Instant createdAt,
  String updatedBy,
  Instant updatedAt
) {

  public WordEntity {
    translations = translations == null ? List.of() : List.copyOf(translations);
  }

  public static WordEntity createManual(
    String russian,
    List<WordTranslationEntity> translations,
    WordType type,
    WordAspect aspect,
    WordFormsEntity forms
  ) {
    return new WordEntity(
      UUID.randomUUID(), null, russian, manualTranslationsWithOrigin(translations), null, null, null,
      type, aspect, forms, null, null, null, null, null
    );
  }

  public WordEntity replaceManualTranslations(
    String russian,
    List<WordTranslationEntity> manualTranslations,
    WordType type,
    WordAspect aspect,
    WordFormsEntity forms
  ) {
    List<WordTranslationEntity> mergedTranslations = new ArrayList<>();
    translations().stream()
      .filter(translation -> translation.managedBy() == TranslationOrigin.OPENRUSSIAN)
      .forEach(mergedTranslations::add);
    mergedTranslations.addAll(manualTranslationsWithOrigin(manualTranslations));

    return new WordEntity(
      id(), externalId(), russian, mergedTranslations, usage(), audioUrl(), checksum(), type,
      aspect, forms,
      publishedAt(), createdBy(), createdAt(), updatedBy(), updatedAt()
    );
  }

  public WordEntity updateWordDetails(String russian, WordType type, WordAspect aspect, WordFormsEntity forms) {
    return new WordEntity(
      id(), externalId(), russian, translations(), usage(), audioUrl(), checksum(), type,
      aspect, forms,
      publishedAt(), createdBy(), createdAt(), updatedBy(), updatedAt()
    );
  }

  public static WordEntity createImported(
    String externalId,
    String russian,
    List<WordTranslationEntity> importedTranslations,
    String usage,
    String audioUrl,
    WordType type,
    WordAspect aspect,
    WordFormsEntity forms
  ) {
    return new WordEntity(
      UUID.randomUUID(), externalId, russian, importedTranslationsWithOrigin(importedTranslations), usage, audioUrl,
      null, type, aspect, forms, Instant.now(), null, null, null, null
    );
  }

  public WordEntity replaceImportedData(
    String externalId,
    String russian,
    List<WordTranslationEntity> importedTranslations,
    String usage,
    String audioUrl,
    WordType type,
    WordAspect aspect,
    WordFormsEntity forms
  ) {
    List<WordTranslationEntity> mergedTranslations = new ArrayList<>(importedTranslationsWithOrigin(importedTranslations));
    translations().stream()
      .filter(translation -> translation.managedBy() == TranslationOrigin.MANUAL)
      .forEach(mergedTranslations::add);

    return new WordEntity(
      id(), externalId, russian, List.copyOf(mergedTranslations), usage, audioUrl, checksum(), type, aspect,
      forms,
      publishedAt(), createdBy(), createdAt(), updatedBy(), updatedAt()
    );
  }

  public WordEntity withChecksum(String checksum) {
    return new WordEntity(
      id(), externalId(), russian(), translations(), usage(), audioUrl(), checksum,
      type(), aspect(), forms(), publishedAt(), createdBy(), createdAt(), updatedBy(), updatedAt()
    );
  }

  public WordEntity publish() {
    return this.withPublishedAt(Instant.now());
  }

  private static List<WordTranslationEntity> importedTranslationsWithOrigin(List<WordTranslationEntity> translations) {
    return translationsWithOriginAndUniquePositions(translations, TranslationOrigin.OPENRUSSIAN);
  }

  private static List<WordTranslationEntity> manualTranslationsWithOrigin(List<WordTranslationEntity> translations) {
    return translationsWithOriginAndUniquePositions(translations, TranslationOrigin.MANUAL);
  }

  private static List<WordTranslationEntity> translationsWithOriginAndUniquePositions(
    List<WordTranslationEntity> translations,
    TranslationOrigin origin
  ) {
    if (translations == null) {
      return List.of();
    }

    Map<String, Set<Integer>> positionsByLanguage = new HashMap<>();
    List<WordTranslationEntity> normalized = new ArrayList<>(translations.size());
    for (WordTranslationEntity translation : translations) {
      Set<Integer> usedPositions = positionsByLanguage.computeIfAbsent(
        translation.language(), ignored -> new HashSet<>()
      );
      int position = translation.position();
      while (!usedPositions.add(position)) {
        if (position == Integer.MAX_VALUE) {
          throw new IllegalArgumentException("Translation positions exceed supported range");
        }
        position++;
      }
      normalized.add(new WordTranslationEntity(
        translation.language(), translation.text(), origin, position
      ));
    }
    return List.copyOf(normalized);
  }
}
