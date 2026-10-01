package es.pausegarra.russian_cheatsheet.context.words.infrastructure.models;

import es.pausegarra.russian_cheatsheet.common.infrastructure.audit.AuditableModel;
import es.pausegarra.russian_cheatsheet.context.words.domain.entities.ExampleSentenceEntity;
import es.pausegarra.russian_cheatsheet.context.words.domain.entities.ExampleTranslationEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(
  name = "example_sentences",
  indexes = {@Index(name = "example_sentences_external_id_idx", columnList = "external_id", unique = true)}
)
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
@Getter
@SuperBuilder(toBuilder = true)
public class ExampleSentenceModel extends AuditableModel {

  @Id
  private final UUID id;

  @Column(name = "external_id", nullable = false)
  private final String externalId;

  @Column(nullable = false, columnDefinition = "text")
  private final String russian;

  @Column(columnDefinition = "jsonb", nullable = false)
  @JdbcTypeCode(SqlTypes.JSON)
  private final List<ExampleTranslationJson> translations;

  private final String contributor;

  @Column(name = "audio_url", length = 2048)
  private final String audioUrl;

  @Column(length = 64)
  private final String checksum;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
    name = "word_examples",
    joinColumns = @JoinColumn(name = "example_id"),
    inverseJoinColumns = @JoinColumn(name = "word_id")
  )
  private final List<WordModel> linkedWords;

  public static ExampleSentenceModel fromEntity(ExampleSentenceEntity entity, List<WordModel> linkedWords) {
    List<ExampleTranslationJson> translationsJson = entity.translations().stream()
      .map(translation -> new ExampleTranslationJson(translation.language(), translation.text(), translation.position()))
      .toList();

    return ExampleSentenceModel.builder()
      .id(entity.id())
      .externalId(entity.externalId())
      .russian(entity.russian())
      .translations(translationsJson)
      .contributor(entity.contributor())
      .audioUrl(entity.audioUrl())
      .checksum(entity.checksum())
      .linkedWords(new ArrayList<>(linkedWords))
      .createdBy(entity.createdBy())
      .createdAt(entity.createdAt())
      .updatedBy(entity.updatedBy())
      .updatedAt(entity.updatedAt())
      .build();
  }

  public ExampleSentenceEntity toEntity() {
    List<ExampleTranslationEntity> translationEntities = translations == null
      ? List.of()
      : translations.stream()
        .map(translation -> new ExampleTranslationEntity(translation.language(), translation.text(), translation.position()))
        .toList();
    List<String> wordExternalIds = linkedWords == null
      ? List.of()
      : linkedWords.stream()
        .map(WordModel::getExternalId)
        .filter(externalId -> externalId != null)
        .sorted()
        .toList();

    return new ExampleSentenceEntity(
      id, externalId, russian, translationEntities, contributor, audioUrl, checksum,
      wordExternalIds, getCreatedBy(), getCreatedAt(), getUpdatedBy(), getUpdatedAt()
    );
  }

}
