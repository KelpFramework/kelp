package de.pxav.kelp.core.event.kelpevent;

import de.pxav.kelp.core.inventory.type.KelpInventory;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

/**
 * A class description goes here.
 *
 * @author pxav
 */
public class KelpInventoryUpdateEvent extends PlayerEvent {

  private static final HandlerList handlers = new HandlerList();
  private KelpInventory inventory;

  public KelpInventoryUpdateEvent(Player who, KelpInventory inventory) {
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
}
