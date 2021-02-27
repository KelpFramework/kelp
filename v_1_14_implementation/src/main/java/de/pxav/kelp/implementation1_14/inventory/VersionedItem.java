package de.pxav.kelp.implementation1_14.inventory;

import de.pxav.kelp.core.inventory.item.KelpItem;
import de.pxav.kelp.core.inventory.material.KelpMaterial;
import de.pxav.kelp.core.inventory.version.ItemVersionTemplate;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.List;

/**
 * A class description goes here.
 *
 * @author pxav
 */
public class VersionedItem extends ItemVersionTemplate {
  @Override
  public KelpItem fromItemStack(ItemStack itemStack) {
    return null;
  }

  @Override
  public ItemStack newItemStack() {
    return null;
  }

  @Override
  public ItemStack newItemStack(KelpMaterial material) {
    return null;
  }

  @Override
  public ItemStack setDisplayName(ItemStack itemStack, String displayName) {
    return null;
  }

  @Override
  public ItemStack setLore(ItemStack itemStack, List<String> itemLore) {
    return null;
  }

  @Override
  public ItemStack makeUnbreakable(ItemStack itemStack) {
    return null;
  }

  @Override
  public ItemStack makeBreakable(ItemStack itemStack) {
    return null;
  }

  @Override
  public ItemStack addEnchantment(Enchantment enchantment, int level, ItemStack to) {
    return null;
  }

  @Override
  public ItemStack addUnsafeEnchantment(Enchantment enchantment, int level, ItemStack to) {
    return null;
  }

  @Override
  public ItemStack removeEnchantment(Enchantment enchantment, ItemStack from) {
    return null;
  }

  @Override
  public ItemStack addItemFlag(ItemFlag itemFlag, ItemStack from) {
    return null;
  }

  @Override
  public ItemStack removeItemFlag(ItemFlag itemFlag, ItemStack from) {
    return null;
  }
}
