package de.pxav.kelp.core.inventory.version;

import de.pxav.kelp.core.application.KelpVersionTemplate;
import org.bukkit.inventory.Inventory;

/**
 * A class description goes here.
 *
 * @author pxav
 */
@KelpVersionTemplate
public abstract class InventoryVersionTemplate {

  public abstract Inventory createInventory(int size, String title);


}
