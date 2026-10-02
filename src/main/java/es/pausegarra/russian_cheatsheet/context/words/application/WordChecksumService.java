package es.pausegarra.russian_cheatsheet.context.words.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.annotation.JsonInclude;
import es.pausegarra.russian_cheatsheet.common.domain.exception.InternalServerError;
import es.pausegarra.russian_cheatsheet.context.words.domain.entities.WordEntity;
import es.pausegarra.russian_cheatsheet.context.words.domain.entities.WordFormsEntity;
import es.pausegarra.russian_cheatsheet.context.words.domain.entities.WordTranslationEntity;
import es.pausegarra.russian_cheatsheet.context.words.domain.enums.TranslationOrigin;
import jakarta.enterprise.context.ApplicationScoped;
import io.quarkus.runtime.annotations.RegisterForReflection;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Comparator;
import java.util.HexFormat;
import java.util.List;

@ApplicationScoped
@RegisterForReflection(targets = {
  WordFormsEntity.class,
  WordTranslationEntity.class
})
public class WordChecksumService {

  private static final ObjectMapper CHECKSUM_MAPPER = new ObjectMapper()
    .setSerializationInclusion(JsonInclude.Include.ALWAYS)
    .enable(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY)
    .enable(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS);

  public String calculate(WordEntity word) {
    List<ChecksumTranslation> translations = word.translations().stream()
      .filter(translation -> translation.managedBy() == TranslationOrigin.OPENRUSSIAN)
      .sorted(Comparator.comparing(WordTranslationEntity::language)
        .thenComparing(WordTranslationEntity::text))
      .map(translation -> new ChecksumTranslation(translation.language(), translation.text()))
      .toList();

    CanonicalWord payload = new CanonicalWord(
      word.russian(), word.type() == null ? null : word.type().value(), translations, word.usage(), word.audioUrl(),
      word.aspect() == null ? null : word.aspect().value(), word.forms()
    );

    try {
      byte[] serialized = CHECKSUM_MAPPER.writeValueAsBytes(payload);
      return HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256").digest(serialized));
    } catch (JsonProcessingException | NoSuchAlgorithmException exception) {
      throw new InternalServerError("Could not calculate word checksum", exception);
    }
  }

  @RegisterForReflection
  @JsonInclude(JsonInclude.Include.ALWAYS)
  private record CanonicalWord(
    String russian,
    String type,
    List<ChecksumTranslation> translations,
    String usage,
    String audioUrl,
    String aspect,
    WordFormsEntity forms
  ) {}

  @RegisterForReflection
  @JsonInclude(JsonInclude.Include.ALWAYS)
  private record ChecksumTranslation(String language, String text) {}

}
