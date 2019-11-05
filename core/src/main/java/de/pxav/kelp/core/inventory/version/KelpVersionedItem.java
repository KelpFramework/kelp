package de.pxav.kelp.core.inventory.version;

import de.pxav.kelp.core.version.material.KelpMaterial;
import org.bukkit.inventory.ItemStack;

/**
 * A class description goes here.
 *
 * @author pxav
 */
public abstract class KelpVersionedItem {

  public abstract ItemStack newItemStack();

  public abstract ItemStack newItemStack(KelpMaterial material);

  public abstract ItemStack newItemStack(KelpMaterial material, int subId);

  public abstract ItemStack newItemStack(KelpMaterial material, short subId);

  public abstract ItemStack newItemStack(KelpMaterial material, short subId, int amount);

  public abstract void setDisplayName()

}
