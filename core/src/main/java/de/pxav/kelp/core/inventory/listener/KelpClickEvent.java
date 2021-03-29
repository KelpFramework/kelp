package de.pxav.kelp.core.inventory.listener;

import de.pxav.kelp.core.inventory.item.KelpItem;
import de.pxav.kelp.core.inventory.type.KelpInventory;
import de.pxav.kelp.core.player.KelpPlayer;

import java.util.function.Consumer;

/**
 * This event class is used by Kelp's integrated in-line item
 * click listener. If you create a listener for a {@link KelpItem kelp item}
 * using {@link KelpItem#addGlobalListener(Consumer)} for example, a consumer
 * wrapping this class is passed and accepted every time the listener is triggered.
 *
 * This event is not meant for normal use with the {@link de.pxav.kelp.core.event.listener.KelpListener}
 * system or {@code @EventHandler}s by spigot, but only for in-line item listeners.
 *
 * @author pxav
 */
public class KelpClickEvent {

  private KelpPlayer player;
  private KelpInventory<?> inventory;
  private KelpItem clickedItem;

  public KelpClickEvent(KelpPlayer player, KelpInventory<?> inventory, KelpItem clickedItem) {
    this.player = player;
    this.inventory = inventory;
    this.clickedItem = clickedItem;
  }

  /**
   * Gets the player who triggered this event / who clicked the item.
   *
   * @return The player who clicked the item you are listening for.
   */
  public KelpPlayer getPlayer() {
    return player;
  }

  /**
   * Gets the inventory this click event has been performed in.
   *
   * @return The inventory this click has been performed in.
   */
  public KelpInventory<?> getInventory() {
    return inventory;
  }

  /**
   * Gets the item that has been clicked.
   *
   * @return The clicked item.
   */
  public KelpItem getClickedItem() {
    return clickedItem;
  }

}
