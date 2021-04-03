package de.pxav.kelp.core.inventory.enchant.custom;

import de.pxav.kelp.core.inventory.enchant.KelpEnchantment;
import de.pxav.kelp.core.inventory.item.KelpItem;
import de.pxav.kelp.core.inventory.material.KelpMaterial;

/**
 * This enchantment can be applied to an item if you want to make it
 * glow without giving it a special ability or showing the enchantment
 * in the item lore/interfering with "real" enchantments.
 *
 * This enchantment can be applied to any item and can be combined with
 * any other enchantment safely. It won't be added to the item's lore.
 *
 * You can directly use the glow effect via the shortcuts provided by
 * the kelp item class:
 * - {@link KelpItem#addGlow()}
 * - {@link KelpItem#removeGlow()}
 * - {@link KelpItem#setGlowing(boolean)}
 * - {@link KelpItem#hasGlow()}
 *
 * @author pxav
 */
public final class ItemGlowEnchantment extends KelpEnchantment {

  @Override
  public String getName() {
    return "Item Glow";
  }

  @Override
  public int getStartLevel() {
    return 1;
  }

  @Override
  public int getMaxLevel() {
    return 1;
  }

  @Override
  public boolean canEnchant(KelpMaterial material) {
    return true;
  }

  @Override
  public boolean conflictsWith(Class<? extends KelpEnchantment> enchantment) {
    return false;
  }

  @Override
  public boolean addToItemDescription() {
    return false;
  }

}
