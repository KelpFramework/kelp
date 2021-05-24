package de.pxav.kelp.core.inventory.widget;

import de.pxav.kelp.core.inventory.item.KelpItem;
import de.pxav.kelp.core.player.KelpPlayer;

import java.util.Collection;
import java.util.Set;

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
   * @param player The widget "receiver".
   * @return The collection of items representing the widget.
   */
  Collection<KelpItem> render(KelpPlayer player);

  /**
   * Gets all slots covered by this widget in the
   * target inventory.
   * IMPORTANT: If your widget has not been rendered at least
   * once, this must return either {@code null} or an empty set.
   *
   * @return A set of all slots covered by this widget in the target inventory.
   */
  Set<Integer> getCoveredSlots();

}
