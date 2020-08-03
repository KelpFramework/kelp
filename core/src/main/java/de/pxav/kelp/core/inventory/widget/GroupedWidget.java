package de.pxav.kelp.core.inventory.widget;

import de.pxav.kelp.core.inventory.item.KelpItem;

import java.util.Collection;

/**
 * This interface represents a widget type.
 *
 * A grouped widget is a widget, which consists of
 * multiple items. It produces a list of items, which
 * have to be put into the final inventory.
 *
 * If you only want to add a single item to the inventory,
 * consider using a {@code SimpleWidget}.
 *
 * @author pxav
 * @see SimpleWidget
 * @see Widget
 */
public interface GroupedWidget extends Widget {

  /**
   * Converts all the given information and customization
   * and generates a collection of items out of it.
   *
   * @return The collection of items representing the widget.
   */
  Collection<KelpItem> render();

}
