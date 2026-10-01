package es.pausegarra.russian_cheatsheet.context.words.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.annotation.JsonInclude;
import es.pausegarra.russian_cheatsheet.common.domain.exception.InternalServerError;
import es.pausegarra.russian_cheatsheet.context.words.domain.entities.ExampleSentenceEntity;
import es.pausegarra.russian_cheatsheet.context.words.domain.entities.ExampleTranslationEntity;
import jakarta.enterprise.context.ApplicationScoped;
import io.quarkus.runtime.annotations.RegisterForReflection;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Comparator;
import java.util.HexFormat;
import java.util.UUID;

@ApplicationScoped
@RegisterForReflection(targets = {ExampleTranslationEntity.class})
public class ExampleChecksumService {

  private static final ObjectMapper CHECKSUM_MAPPER = new ObjectMapper()
    .setSerializationInclusion(JsonInclude.Include.ALWAYS)
    .enable(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY)
    .enable(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS);

  public String calculate(ExampleSentenceEntity sentence) {
    var translations = sentence.translations().stream()
      .sorted(Comparator.comparing(es.pausegarra.russian_cheatsheet.context.words.domain.entities.ExampleTranslationEntity::language)
        .thenComparingInt(es.pausegarra.russian_cheatsheet.context.words.domain.entities.ExampleTranslationEntity::position)
        .thenComparing(es.pausegarra.russian_cheatsheet.context.words.domain.entities.ExampleTranslationEntity::text))
      .map(translation -> new ChecksumTranslation(translation.language(), translation.text(), translation.position()))
      .toList();
    CanonicalSentence payload = new CanonicalSentence(
      sentence.russian(), translations, sentence.contributor(), sentence.audioUrl(), sentence.linkedWordIds()
    );

    try {
      byte[] serialized = CHECKSUM_MAPPER.writeValueAsBytes(payload);
      return HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256").digest(serialized));
    } catch (JsonProcessingException | NoSuchAlgorithmException exception) {
      throw new InternalServerError("Could not calculate example checksum", exception);
    }
  }

  @RegisterForReflection
  @JsonInclude(JsonInclude.Include.ALWAYS)
  private record CanonicalSentence(
    String russian,
    java.util.List<ChecksumTranslation> translations,
    String contributor,
    String audioUrl,
    java.util.List<UUID> linkedWordIds
  ) {}

  @RegisterForReflection
  @JsonInclude(JsonInclude.Include.ALWAYS)
  private record ChecksumTranslation(String language, String text, int position) {}

}
