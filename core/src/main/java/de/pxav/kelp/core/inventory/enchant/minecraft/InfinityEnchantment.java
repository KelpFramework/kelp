package de.pxav.kelp.core.inventory.enchant.minecraft;

import de.pxav.kelp.core.inventory.enchant.KelpEnchantment;
import de.pxav.kelp.core.inventory.material.KelpMaterial;
import de.pxav.kelp.core.version.KelpVersion;

public final class InfinityEnchantment extends KelpEnchantment {

  @Override
  public String getName() {
    return "Infinity";
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
    return material == KelpMaterial.BOW;
  }

  @Override
  public boolean conflictsWith(Class<? extends KelpEnchantment> enchantment) {
    return enchantment == MendingEnchantment.class;
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
