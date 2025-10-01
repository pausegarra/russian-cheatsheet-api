package es.pausegarra.template.common.utils;

import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@UtilityClass
public class ListUtils {

  public <T, R> List<R> extractFieldFromList(List<T> list, Function<T, R> extractor) {
    return list.stream().map(extractor).toList();
  }

  public <T, K> Map<K, T> indexList(List<T> list, Function<T, K> keyExtractor) {
    return list.stream().collect(Collectors.toMap(keyExtractor, item -> item, (item1, item2) -> item1));
  }

  public <T, K, R> Map<K, R> indexList(List<T> list, Function<T, K> keyExtractor, Function<T, R> valueExtractor) {
    return list.stream().collect(Collectors.toMap(keyExtractor, valueExtractor, (item1, item2) -> item1));
  }

}
