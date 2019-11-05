package de.pxav.kelp.core.inventory.item;

/**
 * A class description goes here.
 *
 * @author pxav
 */
public class NbtTag<K, V> {

  public K key;
  public V value;

  public NbtTag(K key, V value) {
    this.key = key;
    this.value = value;
  }

  public void setKey(K key) {
    this.key = key;
  }

  public void setValue(V value) {
    this.value = value;
  }

  public K getKey() {
    return key;
  }

  public V getValue() {
    return value;
  }

}
