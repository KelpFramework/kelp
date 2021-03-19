package de.pxav.kelp.core.common;

import com.google.common.collect.Multimap;

import java.util.Collection;
import java.util.Map;

/**
 * This is a custom implementation of Guava's normal {@link Multimap} with the
 * only difference that this offers thread-safety and some performance optimizations
 * in some operations, while other operations are slightly slower than with a normal
 * {@code Multimap}.
 *
 * @param <K> The key type for each multimap entry
 * @param <V> The value type for each multimap entry.
 */
public interface ConcurrentMultimap<K, V> extends Multimap<K, V> {

  /**
   * Checks whether a single key has all values
   * contained by the given collection.
   *
   * @param iterable The collection to check.
   * @return {@code true} of a single key had all of the values contained by the collection.
   */
  boolean containsValue(Collection<V> iterable);

  /**
   * Gets the collection associated with the given
   * key. If there is no entry for the given key,
   * the given fallback collection will be returned.
   *
   * @param key The key to get the collection of.
   * @param defaultCollection The fallback collection to return
   *                          if there is no collection associated with the given key.
   * @return The collection associated with the given key or the given fallback collection.
   */
  Collection<V> getOrDefault(K key, Collection<V> defaultCollection);

  /**
   * Gets the collection associated with the given
   * key. If there is no entry for the given key,
   * an empty collection (depending on the implementation)
   * will be returned.
   *
   * @param key The key to get the collection of.
   * @return The collection associated with the given key or an empty collection.
   */
  Collection<V> getOrEmpty(K key);

  /**
   * Removes all entries with the given value.
   *
   * @param value The value to remove all entries with.
   */
  void removeWithValue(V value);

  /**
   * Takes a normal map and inserts its values
   * into the multimap. If one of the contained keys
   * already exists, it will simply be added to the collection.
   *
   * @param newMap The map to be added to the multimap.
   */
  void putAll(Map<K, V> newMap);

}
