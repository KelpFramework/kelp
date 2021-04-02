package de.pxav.kelp.core.inventory.enchant.minecraft;

import de.pxav.kelp.core.inventory.enchant.KelpEnchantment;
import de.pxav.kelp.core.inventory.material.KelpMaterial;
import de.pxav.kelp.core.version.KelpVersion;

public final class UnbreakingEnchantment extends KelpEnchantment {

  @Override
  public String getName() {
    return "Unbreaking";
  }

  @Override
  public int getStartLevel() {
    return 0;
  }

  @Override
  public int getMaxLevel() {
    return 0;
  }

  @Override
  public boolean canEnchant(KelpMaterial material) {
    return false;
  }

  @Override
  public boolean conflictsWith(String otherEnchantment) {
    return false;
  }

  @Override
  public boolean addToItemDescription() {
    return false;
  }

  @Override
  protected boolean bukkitEnchantmentUnsafe(KelpVersion version) {
    return true;
  }
}
