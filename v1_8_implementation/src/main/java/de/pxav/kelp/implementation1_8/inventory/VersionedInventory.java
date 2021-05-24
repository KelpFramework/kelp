package de.pxav.kelp.implementation1_8.inventory;

import de.pxav.kelp.core.inventory.type.StorageInventory;
import de.pxav.kelp.core.inventory.version.InventoryVersionTemplate;
import de.pxav.kelp.core.version.Versioned;
import org.bukkit.Bukkit;
import org.bukkit.entity.Horse;
import org.bukkit.inventory.HorseInventory;
import org.bukkit.inventory.Inventory;

/**
 * A class description goes here.
 *
 * @author pxav
 */
@Versioned
public class VersionedInventory extends InventoryVersionTemplate {

  @Override
  public Inventory createInventory(int size, String title) {
    return Bukkit.createInventory(null, size, title);
  }

  @Override
  public StorageInventory<?> getStorageInventory(Inventory bukkitInventory) {
    if (bukkitInventory instanceof HorseInventory) {
      return new VersionedHorseInventory(bukkitInventory);
    }

    return new VersionedStorageInventory<>(bukkitInventory);
  }

}
