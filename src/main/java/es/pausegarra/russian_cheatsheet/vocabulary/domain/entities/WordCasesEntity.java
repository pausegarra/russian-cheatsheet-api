package es.pausegarra.russian_cheatsheet.vocabulary.domain.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "word_cases")
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
@Builder
@Getter
public class WordCasesEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private final UUID id;

  @OneToOne(optional = false)
  @JoinColumn(
    name = "word_id", nullable = false, unique = true
  )
  @With
  private final WordEntity word;

  @Column(name = "nominative")
  private final String nominative;

  @Column(name = "genitive")
  private final String genitive;

  @Column(name = "dative")
  private final String dative;

  @Column(name = "accusative")
  private final String accusative;

  @Column(name = "instrumental")
  private final String instrumental;

  @Column(name = "prepositional")
  private final String prepositional;

  public WordCasesEntity update(WordCasesEntity entity) {
    return new WordCasesEntity(
      getId(),
      getWord(),
      entity.getNominative(),
      entity.getGenitive(),
      entity.getDative(),
      entity.getAccusative(),
      entity.getInstrumental(),
      entity.getPrepositional()
    );
  }

}
