package de.pxav.kelp.core.inventory.widget;

import de.pxav.kelp.core.inventory.item.KelpItem;

/**
 * This interface represents a widget type.
 *
 * A simple widget is a widget, which at the end
 * consists of only one {@code KelpItem}. Although
 * the widget can have multiple states and different
 * looks, only a single item can be seen in the final
 * inventory GUI.
 *
 * If you want to have multiple items for your widgets,
 * consider using {@code GroupedWidget}.
 *
 * @author pxav
 * @see GroupedWidget
 * @see Widget
 */
public interface SimpleWidget extends Widget {

  /**
   * Renders the widget and converts all the given information
   * and configuration to a single {@code KelpItem}, which can
   * be put into the inventory.
   *
   * @return The final {@code KelpItem}.
   */
  KelpItem render();

  /**
   * Gets the slot covered by this widget in the target inventory.
   * IMPORTANT: This number has to be {@link de.pxav.kelp.core.inventory.InventoryConstants#NOT_RENDERED_SIMPLE_WIDGET}
   * if the widget has not been rendered yet.
   *
   * @return The slot covered in the target inventory.
   */
  int getCoveredSlot();

}
