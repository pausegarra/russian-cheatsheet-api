package es.pausegarra.russian_cheatsheet.context.words.infrastructure.models;

import es.pausegarra.russian_cheatsheet.common.infrastructure.audit.AuditableModel;
import es.pausegarra.russian_cheatsheet.context.words.domain.entities.WordEntity;
import es.pausegarra.russian_cheatsheet.context.words.domain.entities.WordFormsEntity;
import es.pausegarra.russian_cheatsheet.context.words.domain.enums.WordAspect;
import es.pausegarra.russian_cheatsheet.context.words.domain.enums.WordType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.FilterDefs;
import org.hibernate.annotations.Filters;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(
  name = "words", indexes = {
    @Index(name = "words_russian_idx", columnList = "russian"),
    @Index(name = "words_external_id_idx", columnList = "external_id", unique = true)
  }
)
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
@Getter
@SuperBuilder(toBuilder = true)
@FilterDefs({
  @FilterDef(name = "publishedAt.notNull"),
  @FilterDef(name = "publishedAt.null")
})
@Filters({
  @Filter(name = "publishedAt.notNull", condition = "published_at is not null"),
  @Filter(name = "publishedAt.null", condition = "published_at is null")
})
public class WordModel extends AuditableModel {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private final UUID id;

  @Column(name = "external_id")
  private final String externalId;

  private final String russian;

  @Column(columnDefinition = "text")
  private final String usage;

  @Column(name = "audio_url", length = 2048)
  private final String audioUrl;

  @Column(length = 64)
  private final String checksum;

  @Convert(converter = WordTypeConverter.class)
  private final WordType type;

  @Convert(converter = WordAspectConverter.class)
  @Column(length = 16)
  private final WordAspect aspect;

  @Column(name = "forms", columnDefinition = "jsonb")
  @JdbcTypeCode(SqlTypes.JSON)
  private final WordFormsEntity forms;

  @Column(name = "published_at")
  private final Instant publishedAt;

  @Setter
  @OneToMany(mappedBy = "word", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
  @OrderBy("language ASC, position ASC, managedBy ASC, text ASC")
  private List<WordTranslationModel> translations = new ArrayList<>();

  public static WordModel fromEntity(WordEntity word) {
    UUID id = word.id() != null ? word.id() : UUID.randomUUID();
    WordModel model = WordModel.builder()
      .id(id)
      .externalId(word.externalId())
      .russian(word.russian())
      .usage(word.usage())
      .audioUrl(word.audioUrl())
      .checksum(word.checksum())
      .type(word.type())
      .aspect(word.aspect())
      .forms(word.forms())
      .publishedAt(word.publishedAt())
      .createdBy(word.createdBy())
      .createdAt(word.createdAt())
      .updatedBy(word.updatedBy())
      .updatedAt(word.updatedAt())
      .translations(new ArrayList<>())
      .build();

    List<WordTranslationModel> translationModels = new ArrayList<>(word.translations().stream()
      .map(translation -> WordTranslationModel.fromEntity(model, translation))
      .toList());
    model.setTranslations(translationModels);

    return model;
  }

  public WordEntity toEntity() {
    return WordEntity.builder()
      .id(id)
      .externalId(externalId)
      .russian(russian)
      .translations(translations.stream().map(WordTranslationModel::toEntity).toList())
      .usage(usage)
      .audioUrl(audioUrl)
      .checksum(checksum)
      .type(type)
      .aspect(aspect)
      .forms(forms)
      .publishedAt(publishedAt)
      .createdBy(createdBy)
      .createdAt(createdAt)
      .updatedBy(updatedBy)
      .updatedAt(updatedAt)
      .build();
  }
}
