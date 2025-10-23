package es.pausegarra.russian_cheatsheet.context.words.infrastructure.repositories;

import es.pausegarra.russian_cheatsheet.context.words.domain.entities.WordEntity;
import es.pausegarra.russian_cheatsheet.context.words.domain.repositories.WordsRepository;
import es.pausegarra.russian_cheatsheet.context.words.infrastructure.models.WordModel;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
@ApplicationScoped
public class WordsPanacheRepository implements WordsRepository, PanacheRepository<WordModel> {

  @Override
  public WordEntity create(WordEntity word) {
    WordModel wordModel = WordModel.fromEntity(word);

    persist(wordModel);

   return wordModel.toEntity();
  }

  @Override
  public Optional<WordEntity> findById(String id) {
    return find("id", id).firstResultOptional()
      .map(WordModel::toEntity);
  }

}
