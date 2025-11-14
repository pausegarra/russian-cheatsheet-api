package es.pausegarra.russian_cheatsheet.context.words.infrastructure.cron;

import es.pausegarra.russian_cheatsheet.common.utils.ListUtils;
import es.pausegarra.russian_cheatsheet.context.words.domain.entities.WordEntity;
import es.pausegarra.russian_cheatsheet.context.words.domain.enums.WordType;
import es.pausegarra.russian_cheatsheet.context.words.domain.repositories.WordsRepository;
import es.pausegarra.russian_cheatsheet.context.words.infrastructure.rest_clients.memrise.ListWordsResponse;
import es.pausegarra.russian_cheatsheet.context.words.infrastructure.rest_clients.memrise.MemriseRestClient;
import io.quarkus.scheduler.Scheduled;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@ApplicationScoped
@RequiredArgsConstructor
@Slf4j
public class FindWordsInMemrise {

  @RestClient
  private MemriseRestClient memriseRestClient;

  private final WordsRepository wordsRepository;

  @Scheduled(cron = "0 0 1 * * ?")
  @Transactional
  public void findWords() {
    Map<String, WordEntity> wordsMap = getWordsMap();

    List<WordEntity> toSave = new ArrayList<>();
    ListWordsResponse response;
    String nextPageToken = null;
    do {
      response = memriseRestClient.getWords(nextPageToken);
      nextPageToken = response.nextPageToken();

      for (MemriseWordDto word : response.learnables()) {
        WordEntity wordEntity = wordsMap.get(word.russian());

        if (wordEntity != null) {
          continue;
        }

        WordEntity newWord = WordEntity.builder()
          .russian(word.russian())
          .spanish(word.spanish())
          .build();
        toSave.add(newWord);
        wordsMap.put(newWord.russian(), newWord);
      }
    } while (response.nextPageToken() != null);

    wordsRepository.save(toSave);
  }

  private Map<String, WordEntity> getWordsMap() {
    List<WordEntity> words = wordsRepository.getAll();

    return ListUtils.indexList(words, WordEntity::russian);
  }

}
