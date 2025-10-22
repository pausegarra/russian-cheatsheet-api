package es.pausegarra.russian_cheatsheet.context.words.infrastructure.models;

import es.pausegarra.russian_cheatsheet.common.infrastructure.audit.AuditableModel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Entity
@Table(name = "word_declinations")
@Getter
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
@SuperBuilder
public class WordDeclinationModel extends AuditableModel {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private final UUID id;

  private final String nominative;

  private final String accusative;

  private final String genitive;

  private final String dative;

  private final String instrumental;

  private final String prepositional;

  @OneToOne
  @JoinColumn(name = "word_id")
  private final WordModel word;

}
