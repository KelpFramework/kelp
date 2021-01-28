package de.pxav.kelp.core.event.kelpevent;

import de.pxav.kelp.core.inventory.type.KelpInventory;
import de.pxav.kelp.core.player.KelpPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

/**
 * A class description goes here.
 *
 * @author pxav
 */
public class KelpInventoryCloseEvent extends KelpPlayerEvent {

  private static final HandlerList handlers = new HandlerList();
  private KelpInventory inventory;
  private boolean animated;

  public KelpInventoryCloseEvent(KelpPlayer who, KelpInventory inventory, boolean isAnimated) {
    super(who);
    this.inventory = inventory;
    this.animated = isAnimated;
  }

  public KelpInventory getInventory() {
    return this.inventory;
  }

  public boolean isAnimated() {
    return animated;
  }

  @Override
  public HandlerList getHandlers() {
    return handlers;
  }

  public static HandlerList getHandlerList() {
    return handlers;
  }

}
