package de.pxav.kelp.implementation1_14.inventory;

import de.pxav.kelp.core.inventory.material.KelpMaterial;
import de.pxav.kelp.core.inventory.version.ItemVersionTemplate;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

/**
 * A class description goes here.
 *
 * @author pxav
 */
public class VersionedItem extends ItemVersionTemplate {
  @Override
  public ItemStack newItemStack() {
    return null;
  }

  @Override
  public ItemStack newItemStack(KelpMaterial material) {
    return null;
  }

  @Override
  public void makeUnbreakable(ItemStack itemStack) {
  }

  @Override
  public void makeBreakable(ItemStack itemStack) {

  }

  @Override
  public void addEnchantment(Enchantment enchantment, int level, ItemStack to) {

  }

  @Override
  public void addUnsafeEnchantment(Enchantment enchantment, int level, ItemStack to) {

  }

  @Override
  public void removeEnchantment(Enchantment enchantment, ItemStack from) {

  }

  @Override
  public void addItemFlag(ItemFlag itemFlag, ItemStack from) {

  }

  @Override
  public void removeItemFlag(ItemFlag itemFlag, ItemStack from) {

  }
}
