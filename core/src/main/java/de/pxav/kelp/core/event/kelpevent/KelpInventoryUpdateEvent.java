package de.pxav.kelp.core.event.kelpevent;

import de.pxav.kelp.core.inventory.type.KelpInventory;
import de.pxav.kelp.core.player.KelpPlayer;
import org.bukkit.event.HandlerList;

/**
 * This event is called when a {@link KelpInventory} is updated. This does not
 * include title updates of animated inventories.
 *
 * @author pxav
 */
public class KelpInventoryUpdateEvent extends KelpPlayerEvent {

  private static final HandlerList handlers = new HandlerList();
  private KelpInventory<?> inventory;

  public KelpInventoryUpdateEvent(KelpPlayer who, KelpInventory<?> inventory) {
    super(who);
    this.inventory = inventory;
  }

  /**
   * Gets the inventory that has been updated.
   *
   * @return the updated inventory
   */
  public KelpInventory<?> getInventory() {
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
