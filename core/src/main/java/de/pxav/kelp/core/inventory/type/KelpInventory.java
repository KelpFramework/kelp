package de.pxav.kelp.core.inventory.type;

import org.bukkit.inventory.Inventory;

/**
 * A class description goes here.
 *
 * @author pxav
 */
public abstract class KelpInventory {

  public abstract Inventory render();

  public abstract void update(Inventory toUpdate);

}
