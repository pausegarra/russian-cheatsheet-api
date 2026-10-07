package es.pausegarra.russian_cheatsheet.context.words.infrastructure.config;

import io.smallrye.config.ConfigMapping;
import jakarta.inject.Singleton;

@ConfigMapping(prefix = "app.batch")
@Singleton
public interface BatchConfig {

  int maxSize();
}
