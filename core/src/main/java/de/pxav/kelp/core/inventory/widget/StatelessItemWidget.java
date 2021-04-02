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
public class StatelessItemWidget extends AbstractWidget<StatelessItemWidget> implements SimpleWidget {

  // the item to be set into the inventory.
  private KelpItem item;

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
    return item;
  }

  @Override
  public int getCoveredSlot() {
    return item.getSlot();
  }

}
