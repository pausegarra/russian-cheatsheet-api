package es.pausegarra.russian_cheatsheet.context.words.domain.entities;

import es.pausegarra.russian_cheatsheet.context.words.domain.enums.WordType;
import es.pausegarra.russian_cheatsheet.context.words.domain.exception.WordCannotHaveConjugations;
import es.pausegarra.russian_cheatsheet.context.words.domain.exception.WordCannotHaveDeclinationMatrix;
import es.pausegarra.russian_cheatsheet.context.words.domain.exception.WordCannotHaveDeclinations;
import lombok.Builder;
import lombok.With;

import java.time.Instant;
import java.util.UUID;

@Builder
public record WordEntity(
  UUID id,
  String russian,
  String english,
  String spanish,
  WordType type,
  @With WordConjugationEntity conjugations,
  @With WordDeclinationEntity declinations,
  @With WordDeclinationMatrixEntity declinationMatrix,
  String createdBy,
  Instant createdAt,
  String updatedBy,
  Instant updatedAt
) {

  public static WordEntity create(String russian, String english, String spanish, WordType type) {
    return WordEntity.builder()
      .russian(russian)
      .english(english)
      .spanish(spanish)
      .type(type)
      .build();
  }

  public WordEntity addConjugations(WordConjugationEntity conjugations) {
    if (!canHaveConjugations()) {
      throw new WordCannotHaveConjugations(russian());
    }

    return this.withConjugations(conjugations);
  }

  public WordEntity addDeclinations(WordDeclinationEntity declinations) {
    if (!canHaveDeclinations()) {
      throw new WordCannotHaveDeclinations(russian());
    }

    return this.withDeclinations(declinations);
  }

  public WordEntity addDeclinationMatrix(WordDeclinationMatrixEntity declinationMatrix) {
    if (!canHaveDeclinationMatrix()) {
      throw new WordCannotHaveDeclinationMatrix(russian());
    }

    return this.withDeclinationMatrix(declinationMatrix);
  }

  public boolean canHaveConjugations() {
    return type() == WordType.VERB;
  }

  public boolean canHaveDeclinations() {
    return type() == WordType.NOUN;
  }

  public boolean canHaveDeclinationMatrix() {
    return type() == WordType.ADJECTIVE || type() == WordType.PRONOUN || type() == WordType.PARTICIPLE || type() == WordType.ORDINAL;
  }

}
