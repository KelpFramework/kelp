package de.pxav.kelp.core.inventory.enchant.minecraft;

import de.pxav.kelp.core.inventory.enchant.KelpEnchantment;
import de.pxav.kelp.core.inventory.material.KelpMaterial;
import de.pxav.kelp.core.version.KelpVersion;

public final class EfficiencyEnchantment extends KelpEnchantment {

  @Override
  public String getName() {
    return "Efficiency";
  }

  @Override
  public int getStartLevel() {
    return 1;
  }

  @Override
  public int getMaxLevel() {
    return 5;
  }

  @Override
  public boolean canEnchant(KelpMaterial material) {
    return false; //TODO specify KelpMaterial.isTool items
  }

  @Override
  public boolean conflictsWith(Class<? extends KelpEnchantment> enchantment) {
    // efficiency generally conflicts with no other enchantments.
    return false;
  }

  @Override
  public boolean addToItemDescription() {
    return true;
  }

  @Override
  protected boolean bukkitEnchantmentUnsafe(KelpVersion version) {
    return true;
  }

}
