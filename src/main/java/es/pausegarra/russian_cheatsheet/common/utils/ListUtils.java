package es.pausegarra.russian_cheatsheet.common.utils;

import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@UtilityClass
public class ListUtils {

  public <T, R> List<R> extractFieldFromList(List<T> list, Function<T, R> extractor) {
    return list.stream().map(extractor).toList();
  }

  public <T, R> List<R> extractFieldFromListWithoutNulls(List<T> list, Function<T, R> extractor) {
    return list.stream().map(extractor).filter(Objects::nonNull).toList();
  }

  public <T, K> Map<K, T> indexList(List<T> list, Function<T, K> keyExtractor) {
    return list.stream().collect(Collectors.toMap(keyExtractor, item -> item, (item1, item2) -> item1));
  }

  public <T, K, R> Map<K, R> indexList(List<T> list, Function<T, K> keyExtractor, Function<T, R> valueExtractor) {
    return list.stream().collect(Collectors.toMap(keyExtractor, valueExtractor, (item1, item2) -> item1));
  }

  @SafeVarargs
  public <R> List<R> merge(List<R>... lists) {
    return Stream.of(lists).flatMap(List::stream).toList();
  }

}
