package es.pausegarra.russian_cheatsheet.integration.words;

import es.pausegarra.russian_cheatsheet.base.IntegrationTest;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class AccentedWordMigrationIT extends IntegrationTest {
  @Inject
  Flyway flyway;

  @Test
  void shouldAddNullableTextAndInvalidateOnlyImportedChecksumsWithoutChangingWords() throws Exception {
    flyway.clean();
    Flyway.configure().configuration(flyway.getConfiguration()).target("2026.10.08.01.00.00").load().migrate();
    try (var connection = flyway.getConfiguration().getDataSource().getConnection();
         var statement = connection.createStatement()) {
      statement.executeUpdate("""
        insert into words (id, external_id, russian, type, checksum) values
        ('00000000-0000-0000-0000-000000000001', 'source-1', 'привет', 'noun', 'old-import-checksum'),
        ('00000000-0000-0000-0000-000000000002', null, 'слово', 'noun', 'manual-checksum')
        """);
    }
    flyway.migrate();
    try (var connection = flyway.getConfiguration().getDataSource().getConnection();
         var statement = connection.createStatement();
         var result = statement.executeQuery("select russian, russian_accented, checksum from words order by id")) {
      assertTrue(result.next());
      assertEquals("привет", result.getString("russian"));
      assertNull(result.getString("russian_accented"));
      assertNull(result.getString("checksum"));
      assertTrue(result.next());
      assertEquals("слово", result.getString("russian"));
      assertNull(result.getString("russian_accented"));
      assertEquals("manual-checksum", result.getString("checksum"));
      assertFalse(result.next());
    }
    try (var connection = flyway.getConfiguration().getDataSource().getConnection();
         var columns = connection.getMetaData().getColumns(null, "public", "words", "russian_accented")) {
      assertTrue(columns.next());
      assertEquals("text", columns.getString("TYPE_NAME"));
      assertEquals("YES", columns.getString("IS_NULLABLE"));
    }
  }
}
