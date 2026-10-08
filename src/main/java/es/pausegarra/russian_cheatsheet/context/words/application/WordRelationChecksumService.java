package es.pausegarra.russian_cheatsheet.context.words.application;

import es.pausegarra.russian_cheatsheet.common.domain.exception.InternalServerError;
import es.pausegarra.russian_cheatsheet.context.words.domain.enums.WordRelationType;
import jakarta.enterprise.context.ApplicationScoped;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;

@ApplicationScoped
public class WordRelationChecksumService {

  public String calculate(String sourceRussian, String relatedRussian, WordRelationType relation) {
    String canonicalValue = sourceRussian + "|" + relatedRussian + "|" + relation.value();
    try {
      return HexFormat.of().formatHex(
        MessageDigest.getInstance("SHA-256").digest(canonicalValue.getBytes(StandardCharsets.UTF_8))
      );
    } catch (NoSuchAlgorithmException exception) {
      throw new InternalServerError("Could not calculate word relation checksum", exception);
    }
  }

}
