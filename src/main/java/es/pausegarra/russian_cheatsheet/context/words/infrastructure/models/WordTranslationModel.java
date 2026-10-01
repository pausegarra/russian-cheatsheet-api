package es.pausegarra.russian_cheatsheet.context.words.infrastructure.models;

import es.pausegarra.russian_cheatsheet.context.words.domain.entities.WordTranslationEntity;
import es.pausegarra.russian_cheatsheet.context.words.domain.enums.TranslationOrigin;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Entity
@Table(
  name = "word_translations",
  uniqueConstraints = @UniqueConstraint(
    name = "word_translations_word_language_owner_position_uk",
    columnNames = {"word_id", "language", "managed_by", "translation_position"}
  )
)
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
@Getter
@SuperBuilder(toBuilder = true)
public class WordTranslationModel {

  @Id
  @Column(name = "translation_id")
  private final UUID id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "word_id", nullable = false)
  private final WordModel word;

  @Column(nullable = false, length = 16)
  private final String language;

  @Column(nullable = false, columnDefinition = "text")
  private final String text;

  @Enumerated(EnumType.STRING)
  @Column(name = "managed_by", nullable = false, length = 32)
  private final TranslationOrigin managedBy;

  @Column(name = "translation_position", nullable = false)
  private final int position;

  public static WordTranslationModel fromEntity(WordModel word, WordTranslationEntity translation) {
    String identity = word.getId() + "|" + translation.language() + "|" + translation.managedBy() + "|" + translation.position();
    UUID id = UUID.nameUUIDFromBytes(identity.getBytes(StandardCharsets.UTF_8));

    return WordTranslationModel.builder()
      .id(id)
      .word(word)
      .language(translation.language())
      .text(translation.text())
      .managedBy(translation.managedBy())
      .position(translation.position())
      .build();
  }

  public WordTranslationEntity toEntity() {
    return new WordTranslationEntity(language, text, managedBy, position);
  }

}
