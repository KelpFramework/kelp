package de.pxav.kelp.core.inventory.version;

import de.pxav.kelp.core.application.KelpVersionTemplate;
import de.pxav.kelp.core.inventory.type.StorageInventory;
import org.bukkit.inventory.Inventory;

/**
 * This version template is responsible for all version
 * specific operations concerning inventory management (creating
 * inventories, modifying their content, etc.)
 *
 * @author pxav
 */
@KelpVersionTemplate
public abstract class InventoryVersionTemplate {

  /**
   * Creates a new bukkit inventory instance.
   *
   * @param size    The size of the desired inventory.
   * @param title   The title of the desired inventory.
   * @return The bukkit inventory based on the passed size and title.
   */
  public abstract Inventory createInventory(int size, String title);

  public abstract StorageInventory<?> getStorageInventory(Inventory bukkitInventory);

}
