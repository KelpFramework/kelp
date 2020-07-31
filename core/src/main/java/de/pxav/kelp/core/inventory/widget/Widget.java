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

}
