package de.pxav.kelp.core.inventory.widget;

import com.google.common.collect.Sets;
import de.pxav.kelp.core.inventory.item.KelpItem;
import de.pxav.kelp.core.inventory.listener.KelpClickEvent;

import java.util.Set;
import java.util.function.Consumer;

/**
 * This widget is used to create single items and put them into
 * the inventory. They do not have any special features
 * by default but can be given listeners to create custom interactions.
 *
 * @author pxav
 */
public class ItemWidget extends AbstractWidget<ItemWidget> implements SimpleWidget {

  // the item to be set into the inventory.
  private KelpItem item;

  // caches listeners to be added to the item, when the item itself is still null.
  private Set<Consumer<KelpClickEvent>> listenerCache;

  ItemWidget() {
    this.listenerCache = Sets.newHashSet();
  }

  public static ItemWidget create() {
    return new ItemWidget();
  }

  /**
   * Sets the item to be put into the inventory.
   *
   * @param item The item you want to set.
   * @return
   */
  public ItemWidget item(KelpItem item) {
    this.item = item;
    return this;
  }

  /**
   * Adds a listener to the item.
   *
   * You can set listeners before the item itself has been set/created.
   * The widget caches the listeners which were added before the item
   * existed and adds them later when they are rendered ({@link #render()})
   *
   * @param listener The listener you want to add.
   * @return The current instance of the widget.
   */
  public ItemWidget addItemListener(Consumer<KelpClickEvent> listener) {
    // cache the listener if it cannot be added to the item directly.
    if (item == null) {
      if (listenerCache == null) {
        listenerCache = Sets.newHashSet();
      }

      listenerCache.add(listener);
      return this;
    }

    // if the item exists, nothing has to be cached
    addClickListener(item, listener);

    return this;
  }

  /**
   * Renders the widget and converts all the given information
   * and configuration to a single {@code KelpItem}, which can
   * be put into the inventory.
   *
   * @return The final {@code KelpItem}.
   */
  @Override
  public KelpItem render() {
    // add cached listeners which were created when then item
    // did not exist.
    listenerCache.forEach(listener -> addClickListener(item, listener));

    return item;
  }

  @Override
  public int getCoveredSlot() {
    return item.getSlot();
  }

}
