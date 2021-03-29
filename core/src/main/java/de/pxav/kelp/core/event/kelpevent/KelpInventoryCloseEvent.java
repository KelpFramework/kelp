package de.pxav.kelp.core.event.kelpevent;

import de.pxav.kelp.core.inventory.type.KelpInventory;
import de.pxav.kelp.core.player.KelpPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

/**
 * This event is called when a player closes their {@link KelpInventory}
 * or it is closed by a plugin.
 *
 * @author pxav
 */
public class KelpInventoryCloseEvent extends KelpPlayerEvent {

  private static final HandlerList handlers = new HandlerList();
  private KelpInventory<?> inventory;
  private boolean animated;

  public KelpInventoryCloseEvent(KelpPlayer who, KelpInventory<?> inventory, boolean isAnimated) {
    super(who);
    this.inventory = inventory;
    this.animated = isAnimated;
  }

  /**
   * Gets the inventory that has been closed.
   *
   * @return The closed inventory
   */
  public KelpInventory<?> getInventory() {
    return this.inventory;
  }

  /**
   * Checks whether the closed inventory has an animated title.
   *
   * @return {@code true} if the title was animated.
   */
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
