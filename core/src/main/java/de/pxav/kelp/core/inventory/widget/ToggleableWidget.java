package de.pxav.kelp.core.inventory.widget;

import de.pxav.kelp.core.inventory.LocatableItem;
import org.bukkit.inventory.ItemStack;

/**
 * A class description goes here.
 *
 * @author pxav
 */
public class ToggleableWidget implements InventoryWidget {

  private boolean condition;
  private int slot;
  private ItemStack whenTrue;
  private ItemStack whenFalse;

  public ToggleableWidget slot(int slot) {
    this.slot = slot;
    return this;
  }

  public ToggleableWidget condition(boolean condition) {
    this.condition = condition;
    return this;
  }

  public ToggleableWidget whenTrue(ItemStack itemStack) {
    this.whenTrue = itemStack;
    return this;
  }

  public ToggleableWidget whenFalse(ItemStack itemStack) {
    this.whenFalse = itemStack;
    return this;
  }

  @Override
  public LocatableItem item() {
    if (condition) {
      return new LocatableItem(whenTrue, slot);
    } else {
      return new LocatableItem(whenFalse, slot);
    }
  }

  @Override
  public Runnable onClick() {
    return null;
  }

}
