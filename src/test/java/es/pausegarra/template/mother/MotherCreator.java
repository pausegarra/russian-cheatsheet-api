package es.pausegarra.template.mother;

import com.github.javafaker.Faker;

import java.util.Locale;

public final class MotherCreator {

  private static final Faker faker = new Faker(Locale.of("en"));

  public static Faker random() {
    return faker;
  }

}
