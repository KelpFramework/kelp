package de.pxav.kelp.core.inventory;

import de.pxav.kelp.core.inventory.item.KelpItem;

/**
 * A class description goes here.
 *
 * @author pxav
 */
public class LocatableKelpItem {

  private KelpItem item;
  private int slot;

  public LocatableKelpItem(KelpItem item, int slot) {
    this.item = item;
    this.slot = slot;
  }

  public LocatableKelpItem item(KelpItem item) {
    this.item = item;
    return this;
  }

  public LocatableKelpItem slot(int slot) {
    this.slot = slot;
    return this;
  }

  public int getSlot() {
    return slot;
  }

  public KelpItem getItem() {
    return item;
  }

}
