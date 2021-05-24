package de.pxav.kelp.core.inventory.type;

import org.bukkit.inventory.InventoryHolder;

public interface InventoryOwner {

  static InventoryOwner from(InventoryHolder holder) {
    return null;
  }

  StorageInventory<?> getInventory();

}
