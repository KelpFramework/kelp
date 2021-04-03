package de.pxav.kelp.core.inventory.enchant.minecraft;

import de.pxav.kelp.core.inventory.enchant.KelpEnchantment;
import de.pxav.kelp.core.inventory.material.KelpMaterial;
import de.pxav.kelp.core.version.KelpVersion;

public final class MendingEnchantment extends KelpEnchantment {

  @Override
  public String getName() {
    return "Mending";
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
    return material.isBreakable();
  }

  @Override
  public boolean conflictsWith(Class<? extends KelpEnchantment> enchantment) {
    return enchantment == InfinityEnchantment.class;
  }

  @Override
  public boolean addToItemDescription() {
    return true;
  }

  @Override
  protected boolean bukkitEnchantmentUnsafe(KelpVersion version) {
    return version.isHigherThanOrEqualTo(KelpVersion.MC_1_9_0);
  }

}
