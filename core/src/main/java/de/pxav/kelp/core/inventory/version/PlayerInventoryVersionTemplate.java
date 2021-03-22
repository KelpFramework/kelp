package de.pxav.kelp.core.inventory.version;

import de.pxav.kelp.core.application.KelpVersionTemplate;
import de.pxav.kelp.core.inventory.item.KelpItem;
import org.bukkit.entity.Player;

import java.util.Set;

@KelpVersionTemplate
public abstract class PlayerInventoryVersionTemplate {

  public abstract Set<KelpItem> getItems(Player player);
  public abstract Set<KelpItem> getHotBarItems(Player player);
  public abstract KelpItem getItemAt(Player player, int slot);

  public abstract KelpItem getHelmet(Player player);
  public abstract KelpItem getChestPlate(Player player);
  public abstract KelpItem getLeggings(Player player);
  public abstract KelpItem getBoots(Player player);

  public abstract void setHelmet(Player player, KelpItem item);
  public abstract void setChestPlate(Player player, KelpItem item);
  public abstract void setLeggings(Player player, KelpItem item);
  public abstract void setBoots(Player player, KelpItem item);

  public abstract void addItem(Player player, KelpItem item);
  public abstract void setItem(Player player, int slot, KelpItem item);

  public abstract KelpItem getItemInHand(Player player);
  public abstract void setItemInHand(Player player, KelpItem item);

  public abstract KelpItem getItemInOffHand(Player player);
  public abstract void setItemInOffHand(Player player, KelpItem item);

}
