package de.pxav.kelp.core.player.hologram.component;

import de.pxav.kelp.core.inventory.item.KelpItem;

public class HoloItemComponent extends HologramComponent<HoloItemComponent> {

  private KelpItem item;

  public static HoloItemComponent create() {
    return new HoloItemComponent();
  }

  public HoloItemComponent item(KelpItem item) {
    this.item = item;
    return this;
  }

  public KelpItem getItem() {
    return item;
  }

}
