package de.pxav.kelp.core.inventory.widget;

import com.google.common.collect.Sets;
import de.pxav.kelp.core.inventory.item.KelpItem;
import de.pxav.kelp.core.inventory.listener.KelpClickEvent;

import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class StatefulItemWidget extends AbstractWidget<StatefulItemWidget> implements SimpleWidget {

  private Supplier<KelpItem> itemSupplier;

  // caches listeners to be added to the item, when the item itself is still null.
  private Set<Consumer<KelpClickEvent>> listenerCache;

  public static StatefulItemWidget create() {
    return new StatefulItemWidget();
  }

  StatefulItemWidget() {
    listenerCache = Sets.newHashSet();
  }

  public StatefulItemWidget item(Supplier<KelpItem> itemSupplier) {
    this.itemSupplier = itemSupplier;
    return this;
  }

  public StatefulItemWidget addItemListener(Consumer<KelpClickEvent> listener) {
    if (listenerCache == null) {
      listenerCache = Sets.newHashSet();
    }

    listenerCache.add(listener);
    return this;
  }

  @Override
  public KelpItem render() {
    // add cached listeners which were created when then item
    // did not exist.
    KelpItem item = itemSupplier.get();
    listenerCache.forEach(listener -> addClickListener(item, listener));

    return item;
  }

  @Override
  public int getCoveredSlot() {
    return itemSupplier.get().getSlot();
  }

}
