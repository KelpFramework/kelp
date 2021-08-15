package de.pxav.kelp.core.player.hologram.line;

import de.pxav.kelp.core.inventory.item.KelpItem;

public class ItemHologramLine implements HologramLine<KelpItem> {

  private KelpItem item;
  private int itemEntityId;

  public static ItemHologramLine create() {
    return new ItemHologramLine();
  }

  public ItemHologramLine item(KelpItem item) {
    this.item = item;
    return this;
  }

  public void setItemEntityId(int itemEntityId) {
    this.itemEntityId = itemEntityId;
  }

  public int getItemEntityId() {
    return itemEntityId;
  }

  @Override
  public KelpItem display() {
    return this.item;
  }

}
