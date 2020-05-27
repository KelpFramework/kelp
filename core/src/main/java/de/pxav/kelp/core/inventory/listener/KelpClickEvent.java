package de.pxav.kelp.core.inventory.listener;

import de.pxav.kelp.core.inventory.item.KelpItem;
import de.pxav.kelp.core.inventory.type.KelpInventory;
import de.pxav.kelp.core.player.KelpPlayer;

/**
 * A class description goes here.
 *
 * @author pxav
 */
public class KelpClickEvent {

  private KelpPlayer player;
  private KelpInventory inventory;
  private KelpItem clickedItem;

  public KelpClickEvent(KelpPlayer player, KelpInventory inventory, KelpItem clickedItem) {
    this.player = player;
    this.inventory = inventory;
    this.clickedItem = clickedItem;
  }

  public KelpPlayer getPlayer() {
    return player;
  }

  public KelpInventory getInventory() {
    return inventory;
  }

  public KelpItem getClickedItem() {
    return clickedItem;
  }

}
