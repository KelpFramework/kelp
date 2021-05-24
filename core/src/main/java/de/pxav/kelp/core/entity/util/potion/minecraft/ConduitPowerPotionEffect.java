package de.pxav.kelp.core.entity.util.potion.minecraft;

import de.pxav.kelp.core.entity.util.potion.KelpEffectRating;
import de.pxav.kelp.core.entity.util.potion.KelpPotionEffectType;
import de.pxav.kelp.core.entity.util.potion.MinecraftPotion;
import de.pxav.kelp.core.inventory.metadata.Color;
import de.pxav.kelp.core.version.KelpVersion;

/**
 * Conduit Power is an area-of-effect status effect given by conduits that
 * combines Water Breathing, Night Vision (only underwater), and Haste (only underwater).
 *
 * @author pxav
 */
@MinecraftPotion(since = KelpVersion.MC_1_13_0)
public class ConduitPowerPotionEffect extends KelpPotionEffectType {

  @Override
  public String getName() {
    return "Conduit Power";
  }

  @Override
  public boolean isInstant() {
    return false;
  }

  @Override
  public Color getColor() {
    return Color.fromHEX("1DC2D1");
  }

  @Override
  public KelpEffectRating getRating() {
    return KelpEffectRating.POSITIVE;
  }

  @Override
  public boolean isBukkitEffectUnsafe(KelpVersion version) {
    return version.isHigherThanOrEqualTo(KelpVersion.MC_1_13_0);
  }

}
