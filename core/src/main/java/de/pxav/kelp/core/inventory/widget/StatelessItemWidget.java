package de.pxav.kelp.core.inventory.widget;

import de.pxav.kelp.core.inventory.InventoryConstants;
import de.pxav.kelp.core.inventory.item.KelpItem;

/**
 * This widget is used to create single items and put them into
 * the inventory. They do not have any special features
 * by default but can be given listeners to create custom interactions.
 *
 * @author pxav
 */
public class StatelessItemWidget extends AbstractWidget<StatelessItemWidget> implements SimpleWidget {

  // the item to be set into the inventory.
  private KelpItem item;
  private int slot = InventoryConstants.NOT_RENDERED_SIMPLE_WIDGET;

  public static StatelessItemWidget create() {
    return new StatelessItemWidget();
  }

  /**
   * Sets the item to be put into the inventory.
   *
   * @param item The item you want to set.
   * @return
   */
  public StatelessItemWidget item(KelpItem item) {
    this.item = item;
    return this;
  }

  @Override
  public boolean isStateful() {
    return false;
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
    slot = item.getSlot();
    return item;
  }

  @Override
  public int getCoveredSlot() {
    return slot;
  }

}
