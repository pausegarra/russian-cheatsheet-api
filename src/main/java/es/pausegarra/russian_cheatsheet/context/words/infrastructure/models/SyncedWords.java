package es.pausegarra.russian_cheatsheet.context.words.infrastructure.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "synced_words")
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
@Getter
public class SyncedWords {

  @Id
  private final String externalId;

}
