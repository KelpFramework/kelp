package de.pxav.kelp.core.inventory.widget;

import org.bukkit.inventory.ItemStack;

/**
 * A class description goes here.
 *
 * @author pxav
 */
public interface KekWidget extends InventoryWidget {

  ItemStack whenTrue(boolean condition);
  ItemStack whenFalse();
  void onToggle();

}
