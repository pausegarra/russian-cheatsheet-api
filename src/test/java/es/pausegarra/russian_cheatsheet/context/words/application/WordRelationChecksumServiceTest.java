package es.pausegarra.russian_cheatsheet.context.words.application;

import es.pausegarra.russian_cheatsheet.context.words.domain.enums.WordRelationType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WordRelationChecksumServiceTest {

  @Test
  void calculatesSha256FromRussianWordsAndRelationType() {
    String checksum = new WordRelationChecksumService().calculate(
      "машина", "автомобиль", WordRelationType.RELATED
    );

    assertEquals("88f462a165b9e825ea004b0dad94e5b9f192636819ea68b206fa73cd098af586", checksum);
  }
}
