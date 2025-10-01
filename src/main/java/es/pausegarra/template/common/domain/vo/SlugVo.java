package es.pausegarra.template.common.domain.vo;

import es.pausegarra.template.common.domain.vo.exception.InvalidSlugException;
import lombok.Getter;

import java.text.Normalizer;
import java.util.Locale;
import java.util.regex.Pattern;

@Getter
public final class SlugVo {

  private static final Pattern VALID_PATTERN = Pattern.compile("^[a-z0-9]+(?:-[a-z0-9]+)*$");

  private static final Pattern NON_LATIN = Pattern.compile("[^\\w-]");

  private static final Pattern WHITESPACE = Pattern.compile("[\\s]");

  private final String value;

  public SlugVo(String value) {
    if (value == null || value.isBlank()) {
      throw new InvalidSlugException(value);
    }
    if (!VALID_PATTERN.matcher(value).matches()) {
      throw new InvalidSlugException(value, "Slug must match the pattern ^[a-z0-9]+(?:-[a-z0-9]+)*$");
    }

    this.value = value;
  }

  public static SlugVo from(String value) {
    String noWhiteSpace = WHITESPACE.matcher(value).replaceAll("-");
    String normalized = Normalizer.normalize(noWhiteSpace, Normalizer.Form.NFD);
    String slug = NON_LATIN.matcher(normalized).replaceAll("");

    slug = slug.replaceAll("-+", "-").replaceAll("^-|-$", "").toLowerCase(Locale.ROOT);

    return new SlugVo(slug);
  }

}
