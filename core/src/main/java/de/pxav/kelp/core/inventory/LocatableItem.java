package de.pxav.kelp.core.inventory;

import org.bukkit.inventory.ItemStack;

/**
 * A class description goes here.
 *
 * @author pxav
 */
public class LocatableItem {

  private ItemStack itemStack;
  private int slot;

  public LocatableItem(ItemStack itemStack, int slot) {
    this.slot = slot;
    this.itemStack = itemStack;
  }

  public LocatableItem() {}

  public int getSlot() {
    return slot;
  }

  public ItemStack getItemStack() {
    return itemStack;
  }

  public LocatableItem slot(int slot) {
    this.slot = slot;
    return this;
  }

  public LocatableItem itemStack(ItemStack itemStack) {
    this.itemStack = itemStack;
    return this;
  }

}
