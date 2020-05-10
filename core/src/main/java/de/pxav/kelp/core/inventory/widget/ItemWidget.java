package de.pxav.kelp.core.inventory.widget;

import de.pxav.kelp.core.inventory.item.KelpItem;

/**
 * A class description goes here.
 *
 * @author pxav
 */
public class ItemWidget implements SimpleWidget {

  private KelpItem item;

  ItemWidget() {}

  public ItemWidget item(KelpItem item) {
    this.item = item;
    return this;
  }

  @Override
  public KelpItem render() {
    return item;
  }

}
