package es.pausegarra.russian_cheatsheet.context.words.application;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import es.pausegarra.russian_cheatsheet.context.words.application.dto.WordDto;
import es.pausegarra.russian_cheatsheet.context.words.application.use_cases.create_word.CreateWordDto;
import es.pausegarra.russian_cheatsheet.context.words.application.use_cases.update_word.UpdateWordDto;
import es.pausegarra.russian_cheatsheet.context.words.domain.entities.WordEntity;
import es.pausegarra.russian_cheatsheet.context.words.infrastructure.models.WordModel;
import org.junit.jupiter.api.Test;

import java.util.UUID;
import java.security.MessageDigest;
import java.util.HexFormat;

import static org.junit.jupiter.api.Assertions.*;

class AccentedWordTest {
  private final ObjectMapper json = new ObjectMapper().findAndRegisterModules()
    .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
  private final WordWriteMapper mapper = new WordWriteMapper();
  private final WordChecksumService checksums = new WordChecksumService();

  @Test
  void shouldRoundTripExactSourceAccentThroughModelAndResponse() throws Exception {
    String source = "  приве'т е\u0301 ё  ";
    WordEntity word = imported(source);
    WordEntity persisted = WordModel.fromEntity(word).toEntity();
    assertEquals(source, json.valueToTree(WordDto.fromEntity(persisted)).path("russianAccented").textValue());
  }

  @Test
  void shouldReplaceSourceAccentOnImportedUpdate() throws Exception {
    WordEntity updated = mapper.fromUpdate(imported("приве'т"), update("при'вет", true, false));
    assertEquals("при'вет", json.valueToTree(WordDto.fromEntity(updated)).path("russianAccented").textValue());
  }

  @Test
  void shouldPreserveSourceAccentWhenManuallyReplacingTranslations() throws Exception {
    WordEntity updated = mapper.fromUpdate(imported("приве'т"), update("ignored", false, true));
    assertEquals("приве'т", json.valueToTree(WordDto.fromEntity(updated)).path("russianAccented").textValue());
  }

  @Test
  void shouldPreserveSourceAccentWhenManuallyEditingMetadata() throws Exception {
    WordEntity updated = mapper.fromUpdate(imported("приве'т"), update(null, false, false));
    assertEquals("изменено", updated.russian());
    assertEquals("приве'т", json.valueToTree(WordDto.fromEntity(updated)).path("russianAccented").textValue());
  }

  @Test
  void shouldNormalizeOnlyBlankAccentToNull() throws Exception {
    for (String accent : new String[] {null, "", " \t\n"}) {
      WordEntity word = imported(accent);
      assertTrue(json.valueToTree(WordDto.fromEntity(word)).path("russianAccented").isNull());
    }
  }

  @Test
  void shouldPreserveNonWhitespaceControlAndFormatAccents() throws Exception {
    for (String accent : new String[] {"\u001c", "\u001f", "\ufeff"}) {
      WordEntity word = imported(accent);
      assertEquals(accent, json.valueToTree(WordDto.fromEntity(WordModel.fromEntity(word).toEntity()))
        .path("russianAccented").textValue());
    }
  }

  @Test
  void shouldNormalizeUnicodeWhiteSpaceAccentsToNull() throws Exception {
    for (String accent : new String[] {"\u00a0", "\u0085", "\u2007", "\u202f"}) {
      WordEntity word = imported(accent);
      assertTrue(json.valueToTree(WordDto.fromEntity(word)).path("russianAccented").isNull());
      assertEquals(checksums.calculate(imported(null)), checksums.calculate(word));
    }
  }

  @Test
  void shouldMatchSharedControlAccentChecksumFixture() throws Exception {
    try (var fixture = getClass().getResourceAsStream("/fixtures/accented-word-control-checksum.json")) {
      assertNotNull(fixture);
      byte[] canonicalJson = fixture.readAllBytes();
      String expected = "89014dd7dcb65e19bed20ca3244ad09856f26bcdb64d4280711a7832d4b2c14d";
      assertEquals(expected, HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256").digest(canonicalJson)));
      assertEquals(expected, checksums.calculate(imported("\u001c")));
    }
  }

  @Test
  void shouldMatchSharedIntakeChecksumFixture() throws Exception {
    try (var fixture = getClass().getResourceAsStream("/fixtures/accented-word-checksum.json")) {
      assertNotNull(fixture);
      byte[] canonicalJson = fixture.readAllBytes();
      String expected = "ede401cee0fb553c48cfdaf9cdd67e963c4de642247a431c86a20c598387e0c5";
      assertEquals(expected, HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256").digest(canonicalJson)));
      assertEquals(expected, checksums.calculate(mapper.fromCreate(json.readValue(canonicalJson, CreateWordDto.class))));
      assertEquals(expected, checksums.calculate(imported("приве'т")));
    }
  }

  @Test
  void shouldChangeChecksumWhenOnlyAccentChanges() throws Exception {
    assertNotEquals(checksums.calculate(imported("приве'т")), checksums.calculate(imported("при'вет")));
    assertNotEquals(checksums.calculate(imported(null)), checksums.calculate(imported("приве'т")));
  }

  private WordEntity imported(String accent) throws Exception {
    var body = json.createObjectNode().put("russian", "привет").put("externalId", "source-1")
      .put("russianAccented", accent).put("type", "noun");
    body.putArray("translations");
    return mapper.fromCreate(json.treeToValue(body, CreateWordDto.class));
  }

  private UpdateWordDto update(String accent, boolean imported, boolean translations) throws Exception {
    var body = json.createObjectNode().put("id", UUID.randomUUID().toString())
      .put("russian", "привет").put("russianAccented", accent).put("type", "noun");
    if (imported) body.put("externalId", "source-1");
    else body.put("russian", "изменено");
    if (imported || translations) body.putArray("translations");
    return json.treeToValue(body, UpdateWordDto.class);
  }
}
