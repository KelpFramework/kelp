package de.pxav.kelp.implementation1_12.inventory;

import de.pxav.kelp.core.inventory.version.InventoryVersionTemplate;
import de.pxav.kelp.core.version.Versioned;
import org.bukkit.Bukkit;
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

}
