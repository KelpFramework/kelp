package de.pxav.kelp.core.inventory.widget;

import de.pxav.kelp.core.inventory.InventoryConstants;
import de.pxav.kelp.core.inventory.item.KelpItem;

import java.util.function.Supplier;

public class StatefulItemWidget extends AbstractWidget<StatefulItemWidget> implements SimpleWidget {

  private Supplier<KelpItem> itemSupplier;
  private int slot = InventoryConstants.NOT_RENDERED_SIMPLE_WIDGET;

  public static StatefulItemWidget create() {
    return new StatefulItemWidget();
  }

  public StatefulItemWidget item(Supplier<KelpItem> itemSupplier) {
    this.itemSupplier = itemSupplier;
    return this;
  }

  @Override
  public boolean isStateful() {
    return true;
  }

  @Override
  public KelpItem render() {
    KelpItem item = itemSupplier.get();
    slot = item.getSlot();
    return item;
  }

  @Override
  public int getCoveredSlot() {
    return slot;
  }

}
