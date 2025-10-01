package es.pausegarra.template.common.domain.vo;

import es.pausegarra.template.common.domain.vo.SlugVo;
import es.pausegarra.template.common.domain.vo.exception.InvalidSlugException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SlugVoTest {

  @Test
  public void shouldCreateAndParesSlugFromString() {
    SlugVo slug = SlugVo.from("SOME Slug");

    assertEquals("some-slug", slug.getValue());
  }

  @Test
  public void shouldRemoveSpecialChars() {
    SlugVo slug = SlugVo.from("SOME Slug @#!&%()+=");

    assertEquals("some-slug", slug.getValue());
  }

  @Test
  public void ifStringIsWellFormattedShouldReturnSlug() {
    SlugVo slug = SlugVo.from("some-slug");

    assertEquals("some-slug", slug.getValue());
  }

  @Test
  public void ifSlugIsNotValidShouldThrowException() {
    assertThrows(InvalidSlugException.class, () -> new SlugVo("some-slug!"));
    assertThrows(InvalidSlugException.class, () -> new SlugVo(null));
    assertThrows(InvalidSlugException.class, () -> new SlugVo(""));
  }

}
