package de.pxav.kelp.core.event.kelpevent;

import de.pxav.kelp.core.inventory.type.KelpInventory;
import de.pxav.kelp.core.player.KelpPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

/**
 * This event is triggered when a player opens a {@link KelpInventory} or
 * the inventory is opened by a plugin. This does not include opening the player's
 * personal inventory, which is opened by pressing {@code 'E'}.
 *
 * @author pxav
 */
public class KelpInventoryOpenEvent extends KelpPlayerEvent {

  private static final HandlerList handlers = new HandlerList();
  private KelpInventory<?> inventory;
  private boolean animatedInventory;

  public KelpInventoryOpenEvent(KelpPlayer who, KelpInventory<?> inventory, boolean isAnimated) {
    super(who);
    this.inventory = inventory;
    this.animatedInventory = isAnimated;
  }

  public KelpInventory<?> getInventory() {
    return inventory;
  }

  /**
   * Checks if the inventory is a subclass of {@link de.pxav.kelp.core.inventory.type.AnimatedInventory},
   * which would mean that the title is animated.
   *
   * @return {@code true} if the inventory's title is animated.
   */
  public boolean isAnimatedInventory() {
    return animatedInventory;
  }

  @Override
  public HandlerList getHandlers() {
    return handlers;
  }

  public static HandlerList getHandlerList() {
    return handlers;
  }

}
