package de.pxav.kelp.core.entity.type;

/**
 * This enum expresses how an item should be dropped.
 * There are different modes an item can be dropped.
 *
 * @author pxav
 */
public enum ItemDropType {

  /**
   * Drops the item at the provided location without any
   * random spread or offset. It is guaranteed that the item will
   * land at the given location.
   */
  NORMAL,

  /**
   * Drops the item with a natural random offset. That means
   * the location you provide for an item to land at won't be the
   * exact location where it spawns but minecraft will calculate a
   * random location within a small radius and drop it there, which
   * gives that natural-looking effect.
   */
  NATURAL

}
