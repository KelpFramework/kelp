package de.pxav.kelp.implementation1_8.inventory;

import com.google.common.collect.Sets;
import de.pxav.kelp.core.inventory.item.KelpItem;
import de.pxav.kelp.core.inventory.version.PlayerInventoryVersionTemplate;
import de.pxav.kelp.core.version.Versioned;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Set;

@Versioned
public class VersionedPlayerInventory extends PlayerInventoryVersionTemplate {

  @Override
  public Set<KelpItem> getItems(Player player) {
    Set<KelpItem> result = Sets.newHashSet();
    for (int i = 0; i < 41; i++) {
      ItemStack itemStack = player.getInventory().getItem(i);
      if (itemStack != null) {
        result.add(KelpItem.from(itemStack).slot(i));
      }
    }
    return result;
  }

  @Override
  public Set<KelpItem> getHotBarItems(Player player) {
    Set<KelpItem> result = Sets.newHashSet();
    for (int i = 0; i < 9; i++) {
      ItemStack item = player.getInventory().getItem(i);
      if (item != null) {
        result.add(KelpItem.from(item).slot(i));
      }
    }
    return result;
  }

  @Override
  public KelpItem getItemAt(Player player, int slot) {
    return KelpItem.from(player.getInventory().getItem(slot));
  }

  @Override
  public KelpItem getHelmet(Player player) {
    return KelpItem.from(player.getInventory().getHelmet());
  }

  @Override
  public KelpItem getChestPlate(Player player) {
    return KelpItem.from(player.getInventory().getChestplate());
  }

  @Override
  public KelpItem getLeggings(Player player) {
    return KelpItem.from(player.getInventory().getLeggings());
  }

  @Override
  public KelpItem getBoots(Player player) {
    return KelpItem.from(player.getInventory().getBoots());
  }

  @Override
  public void setHelmet(Player player, KelpItem item) {
    player.getInventory().setHelmet(item.getItemStack());
  }

  @Override
  public void setChestPlate(Player player, KelpItem item) {
    player.getInventory().setChestplate(item.getItemStack());
  }

  @Override
  public void setLeggings(Player player, KelpItem item) {
    player.getInventory().setLeggings(item.getItemStack());
  }

  @Override
  public void setBoots(Player player, KelpItem item) {
    player.getInventory().setBoots(item.getItemStack());
  }

  @Override
  public void addItem(Player player, KelpItem item) {
    player.getInventory().addItem(item.getItemStack());
  }

  @Override
  public void setItem(Player player, int slot, KelpItem item) {
    player.getInventory().setItem(slot, item.getItemStack());
  }

  @Override
  public KelpItem getItemInHand(Player player) {
    return KelpItem.from(player.getInventory().getItemInHand());
  }

  @Override
  public void setItemInHand(Player player, KelpItem item) {
    player.setItemInHand(item.getItemStack());
  }

  @Override
  public KelpItem getItemInOffHand(Player player) {
    return null;
  }

  @Override
  public void setItemInOffHand(Player player, KelpItem item) {}
}
