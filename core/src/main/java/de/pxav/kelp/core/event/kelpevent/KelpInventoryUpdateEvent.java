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
public class KelpInventoryUpdateEvent extends KelpPlayerEvent {

  private static final HandlerList handlers = new HandlerList();
  private KelpInventory inventory;

  public KelpInventoryUpdateEvent(KelpPlayer who, KelpInventory inventory) {
    super(who);
    this.inventory = inventory;
  }

  public KelpInventory getInventory() {
    return inventory;
  }

  @Override
  public HandlerList getHandlers() {
    return handlers;
  }

  public static HandlerList getHandlerList() {
    return handlers;
  }

}
