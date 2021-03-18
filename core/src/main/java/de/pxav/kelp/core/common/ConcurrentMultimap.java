package de.pxav.kelp.core.common;

import com.google.common.collect.Multimap;

import java.util.Collection;
import java.util.Map;

public interface ConcurrentMultimap<K, V> extends Multimap<K, V> {

  boolean containsValue(Collection<V> iterable);

  Collection<V> getOrDefault(K key, Collection<V> defaultCollection);

  Collection<V> getOrEmpty(K key);

  void removeWithValue(V value);

  void putAll(Map<K, V> newMap);

}
