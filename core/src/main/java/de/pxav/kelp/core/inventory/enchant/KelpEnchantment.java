package de.pxav.kelp.core.inventory.enchant;

import de.pxav.kelp.core.KelpServer;
import de.pxav.kelp.core.inventory.enchant.minecraft.EfficiencyEnchantment;
import de.pxav.kelp.core.inventory.enchant.minecraft.UnbreakingEnchantment;
import de.pxav.kelp.core.inventory.material.KelpMaterial;
import de.pxav.kelp.core.version.KelpVersion;

public abstract class KelpEnchantment {

  public static final Class<EfficiencyEnchantment> EFFICIENCY = EfficiencyEnchantment.class;

  public static final Class<UnbreakingEnchantment> UNBREAKING = UnbreakingEnchantment.class;

  public abstract String getName();

  public abstract int getStartLevel();

  public abstract int getMaxLevel();

  public abstract boolean canEnchant(KelpMaterial material);

  public abstract boolean conflictsWith(String enchantmentName);

  public abstract boolean addToItemDescription();

  public boolean isBukkitEnchantment() {
    return bukkitEnchantmentUnsafe(KelpServer.getVersion());
  }

  protected boolean bukkitEnchantmentUnsafe(KelpVersion version) {
    return false;
  }

}
