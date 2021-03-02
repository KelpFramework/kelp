package de.pxav.kelp.core.inventory.widget;

import de.pxav.kelp.core.inventory.item.KelpItem;
import de.pxav.kelp.core.inventory.listener.ClickListener;
import de.pxav.kelp.core.player.KelpPlayer;

/**
 * @author Etrayed
 */
public abstract class AbstractWidget<W extends AbstractWidget<?>> implements Widget {

  protected KelpPlayer player;

  /**
   * Sets the player to whom the current widget is currently dedicated.
   *
   * @param player The player you want to choose.
   * @return The current instance of the widget.
   */
  @Override
  public final W player(KelpPlayer player) {
    this.player = player;

    return (W) this;
  }

  /**
   * Gets the player to whom the current widget is dedicated.
   *
   * @return The {@code KelpPlayer} - "owner" of the widget.
   */
  @Override
  public final KelpPlayer getPlayer() {
    return this.player;
  }

  protected void addClickListener(KelpItem item, ClickListener listener) {
    if(player == null) {
      item.addGlobalListener(listener);
    } else {
      item.addListener(player, listener);
    }
  }
}
