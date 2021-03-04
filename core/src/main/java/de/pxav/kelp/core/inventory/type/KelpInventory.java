package de.pxav.kelp.core.inventory.type;

import de.pxav.kelp.core.player.KelpPlayer;
import org.bukkit.inventory.Inventory;

/**
 * A class description goes here.
 *
 * @author pxav
 */
public abstract class KelpInventory {

  public abstract Inventory render(KelpPlayer player);

  public abstract void update(KelpPlayer toUpdate);

}
