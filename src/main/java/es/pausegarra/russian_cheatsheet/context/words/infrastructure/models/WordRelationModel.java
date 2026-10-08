package es.pausegarra.russian_cheatsheet.context.words.infrastructure.models;

import es.pausegarra.russian_cheatsheet.context.words.domain.enums.WordRelationType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.UUID;

@Entity
@Table(
  name = "word_relations",
  uniqueConstraints = @UniqueConstraint(
    name = "word_relations_source_target_relation_uidx",
    columnNames = {"source_word_id", "target_word_id", "relation"}
  ),
  indexes = @Index(name = "word_relations_target_idx", columnList = "target_word_id, source_word_id")
)
@Getter
@Setter
@NoArgsConstructor
public class WordRelationModel {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "source_word_id", nullable = false)
  private WordModel sourceWord;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @OnDelete(action = OnDeleteAction.CASCADE)
  @JoinColumn(name = "target_word_id", nullable = false)
  private WordModel targetWord;

  @Column(name = "source_russian", nullable = false)
  private String sourceRussian;

  @Column(name = "related_russian", nullable = false)
  private String relatedRussian;

  @Convert(converter = WordRelationTypeConverter.class)
  @Column(name = "relation", nullable = false, length = 16)
  private WordRelationType relation;

  @Column(name = "checksum", nullable = false, length = 64)
  private String checksum;
}
