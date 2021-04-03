package de.pxav.kelp.core.inventory.widget;

import de.pxav.kelp.core.inventory.item.KelpItem;
import de.pxav.kelp.core.inventory.listener.KelpClickEvent;
import de.pxav.kelp.core.player.KelpPlayer;

import java.util.function.Consumer;

/**
 * An abstract implementation of the Widget class,
 * which is responsible for player-dependent actions.
 *
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

  /**
   * Adds a listener to the omitted item. If {@link #player the player}
   * is {@code null}, a {@link KelpItem#addGlobalListener(Consumer) global listener} will be added.
   *
   * @param item     The item to listen to.
   * @param listener The listener.
   */
  protected void addClickListener(KelpItem item, Consumer<KelpClickEvent> listener) {
    if(player == null) {
      item.addGlobalListener(listener);
    } else {
      item.addListener(player, listener);
    }
  }
}
