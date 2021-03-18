package de.pxav.kelp.core.common;

import com.google.common.collect.*;
import com.sun.istack.internal.NotNull;

import javax.annotation.Nullable;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class ConcurrentListMultimap<K, V> implements ConcurrentMultimap<K, V> {

  private final ConcurrentMap<K, ArrayList<V>> map;

  public static <K, V> ConcurrentListMultimap<K, V> create() {
    return new ConcurrentListMultimap<>();
  }

  public static <K, V> ConcurrentListMultimap<K, V> create(Multimap<K, V> source) {
    ConcurrentListMultimap<K, V> multimap = new ConcurrentListMultimap<>();
    multimap.putAll(source);
    return multimap;
  }

  public static <K, V> ConcurrentListMultimap<K, V> create(Map<K, V> source) {
    ConcurrentListMultimap<K, V> multimap = new ConcurrentListMultimap<>();
    source.forEach(multimap::put);
    multimap.putAll(source);
    return multimap;
  }

  public ConcurrentListMultimap() {
    this.map = new ConcurrentHashMap<>();
  }

  @Override
  public int size() {
    int size = 0;
    for (Collection<V> value : this.map.values()) {
      size += value.size();
    }
    return size;
  }

  @Override
  public boolean isEmpty() {
    return this.map.isEmpty();
  }

  @Override
  public boolean containsKey(@NotNull Object o) {
    return this.map.containsKey(o);
  }

  @Override
  public boolean containsValue(@NotNull Object o) {
    Iterator<Collection<V>> iterator = this.asMap().values().iterator();

    Collection<V> collection;

    do {
      if (!iterator.hasNext()) {
        return false;
      }

      collection = iterator.next();
    } while(!collection.contains(o));

    return true;
  }

  @Override
  public boolean containsEntry(@NotNull Object o, @NotNull Object o1) {
    Collection<V> collection = this.map.get(o);
    return collection != null && collection.contains(o1);
  }

  @Override
  public boolean put(@NotNull K k, @NotNull V v) {
    if (this.get(k) == null) {
      this.map.put(k, Lists.newArrayList());
    }
    return this.get(k).add(v);
  }

  @Override
  public boolean remove(@NotNull Object key, @NotNull Object value) {
    if (this.map.get(key) == null) {
      return false;
    }
    return this.map.get(key).remove(value);
  }

  @Override
  public boolean putAll(@NotNull K k, Iterable<? extends V> iterable) {
    if (iterable instanceof Collection) {
      Collection<V> collection = (Collection<V>) iterable;
      if (collection.isEmpty()) {
        return false;
      }

      if (this.get(k) == null) {
        this.map.put(k, Lists.newArrayList());
      }
      return this.get(k).addAll(collection);
    } else {
      Iterator<? extends V> valueItr = iterable.iterator();
      if (!valueItr.hasNext()) {
        return false;
      }

      if (this.get(k) == null) {
        this.map.put(k, Lists.newArrayList());
      }
      return Iterators.addAll(this.get(k), valueItr);
    }
  }

  @Override
  public boolean putAll(Multimap<? extends K, ? extends V> multimap) {
    for (Map.Entry<? extends K, ? extends V> entry : multimap.entries()) {
      this.put(entry.getKey(), entry.getValue());
    }
    return false;
  }

  @Override
  public void putAll(Map<K, V> newMap) {
    for (Map.Entry<K, V> entry : newMap.entrySet()) {
      this.put(entry.getKey(), entry.getValue());
    }
  }

  @Override
  public Collection<V> replaceValues(@NotNull K k, Iterable<? extends V> iterable) {
    Collection<V> result = this.removeAll(k);
    this.putAll(k, iterable);
    return result;
  }

  @Override
  public Collection<V> removeAll(@NotNull Object key) {
    if (this.map.get(key) == null) {
      return Lists.newArrayList();
    }
    return this.map.remove(key);
  }

  @Override
  public void clear() {
    this.map.clear();
  }

  @Override
  public Collection<V> get(@Nullable K k) {
    return this.map.get(k);
  }

  @Override
  public Set<K> keySet() {
    return this.map.keySet();
  }

  @Override
  public Multiset<K> keys() {
    return HashMultiset.create(this.map.keySet());
  }

  @Override
  public Collection<V> values() {
    Iterator<Collection<V>> iterator = this.asMap().values().iterator();
    Collection<V> result = Lists.newArrayList();

    while (iterator.hasNext()) {
      result.addAll(iterator.next());
    }

    return result;
  }

  @Override
  public Collection<Map.Entry<K, V>> entries() {
    Collection<Map.Entry<K, V>> entries = Lists.newArrayList();
    this.map.forEach((key, valueSet) -> valueSet.forEach(element -> {
      AbstractMap.SimpleEntry<K, V> entry = new AbstractMap.SimpleEntry<>(key, element);
      entries.add(entry);
    }));
    return entries;
  }


  @Override
  public ConcurrentMap<K, Collection<V>> asMap() {
    ConcurrentMap<K, Collection<V>> output = Maps.newConcurrentMap();
    output.putAll(this.map);
    return output;
  }

  @Override
  public boolean containsValue(Collection<V> iterable) {
    return this.map.containsValue(iterable);
  }

  @Override
  public Collection<V> getOrDefault(K key, Collection<V> defaultCollection) {
    return this.map.getOrDefault(key, Lists.newArrayList(defaultCollection));
  }

  @Override
  public Collection<V> getOrEmpty(K key) {
    return this.map.getOrDefault(key, Lists.newArrayList());
  }

}
