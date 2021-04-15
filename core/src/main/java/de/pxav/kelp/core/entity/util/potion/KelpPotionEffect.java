package de.pxav.kelp.core.entity.util.potion;

import de.pxav.kelp.core.KelpServer;
import de.pxav.kelp.core.entity.util.potion.minecraft.AbsorptionPotionEffect;
import de.pxav.kelp.core.inventory.metadata.Color;
import de.pxav.kelp.core.player.KelpPlayer;
import de.pxav.kelp.core.version.KelpVersion;

public abstract class KelpPotionEffect {

  public static Class<AbsorptionPotionEffect> ABSORPTION = AbsorptionPotionEffect.class;

  public abstract String getName();

  public abstract boolean isInstant();

  public abstract Color getColor();

  public abstract int getDurationForLevel(int level);

  public void onConsume(KelpPlayer player) {}

  public final boolean isBukkitEffect() {
    return isBukkitEffectUnsafe(KelpServer.getVersion());
  }

  public boolean isBukkitEffectUnsafe(KelpVersion version) {
    return false;
  }

}
