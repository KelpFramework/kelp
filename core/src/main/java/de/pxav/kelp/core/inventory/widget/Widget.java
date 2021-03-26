package de.pxav.kelp.core.inventory.widget;

import de.pxav.kelp.core.player.KelpPlayer;

/**
 * This interface is the superclass of every widget
 * provided by the Kelp inventory library.
 *
 * A widget is the most important component of an inventory.
 * The entire content of KelpInventories consists of widgets
 * as they are only a util class taking some information and
 * converting it into final KelpItems to be displayed in the
 * inventory.
 *
 * You can either display only a single item with a widget or
 * handle many different ones.
 *
 * If you want to create a new widget type, it has to inherit
 * from this interface in order to get the both player
 * methods.
 *
 * @author pxav
 * @see SimpleWidget
 * @see GroupedWidget
 */
public interface Widget {

  /**
   * Sets the player to whom the current widget is currently dedicated.
   *
   * @param player The player you want to choose.
   * @return The current instance of the widget.
   */
  Widget player(KelpPlayer player);

  /**
   * Gets the player to whom the current widget is dedicated.
   *
   * @return The {@code KelpPlayer} - "owner" of the widget.
   */
  KelpPlayer getPlayer();

  /**
   * Returns {@code true} if this widget is stateful. A stateful widget
   * is a widget containing updatable content. If you have a widget filling up
   * the empty space in the UI with glass panes, this widget is stateless and
   * updating it every time you update the inventory would be wasted performance.
   * But if you want to display content such as the player's coins or a
   * timer inside an item lore, your item has to be stateful. Only then Kelp
   * will update it every time you call {@link KelpPlayer#updateKelpInventory()}.
   *
   * @return {@code true} if the widget is stateful, {@code false} if it's stateless.
   */
  boolean isStateful();

  default void onRemove() {}

}
