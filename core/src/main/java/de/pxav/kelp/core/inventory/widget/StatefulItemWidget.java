package de.pxav.kelp.core.inventory.widget;

import com.google.common.collect.Sets;
import de.pxav.kelp.core.inventory.item.KelpItem;
import de.pxav.kelp.core.inventory.listener.KelpClickEvent;

import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class StatefulItemWidget extends AbstractWidget<StatefulItemWidget> implements SimpleWidget {

  private Supplier<KelpItem> itemSupplier;

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
    return item;
  }

  @Override
  public int getCoveredSlot() {
    return itemSupplier.get().getSlot();
  }

}
