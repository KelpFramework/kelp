package de.pxav.kelp.core.entity.util.potion.minecraft;

import de.pxav.kelp.core.entity.util.potion.KelpEffectRating;
import de.pxav.kelp.core.entity.util.potion.KelpPotionEffectType;
import de.pxav.kelp.core.entity.util.potion.MinecraftPotion;
import de.pxav.kelp.core.inventory.metadata.Color;
import de.pxav.kelp.core.version.KelpVersion;

/**
 * Regeneration is a status effect that restores a player's (or mob's) health over time.
 *
 * @author pxav
 */
@MinecraftPotion(since = KelpVersion.MC_1_8_0)
public class RegenerationPotionEffect extends KelpPotionEffectType {

  /**
   * Get the amounts of hearts that are regenerated every second based on the
   * level of the effect.
   *
   * If you want to get the health points per second, simply multiply the result
   * of this method by 2.
   *
   * If the level is invalid - so if it is negative for example,
   * {@code -1} will be returned.
   *
   * Amplifiers outside the range 0–31 (corresponding to levels 1–32) are used modulo 32,
   * making level 33 the same as level 1, etc.
   *
   * @param level The level to get the regenerated hearts for.
   * @return The amount of regenerated hearts based on the given level. {@code -1} if the level is invalid.
   */
  public static double getHeartsPerSecond(int level) {
    if (level < 1) {
      return -1;
    }

    // if level limitation of 32 is reached,
    // use numbers as modulo
    if (level > 32) {
      level = level % 32;
    }

    if (level == 1) {
      return 0.2d;
    } else if (level == 2) {
      return 0.4d;
    } else if (level == 3) {
      return 0.835d;
    } else if (level == 4) {
      return 1.665d;
    } else if (level == 5) {
      return 3.335d;

    // level six and higher
    } else {
      return 10d;
    }
  }

  @Override
  public String getName() {
    return "Regeneration";
  }

  @Override
  public boolean isInstant() {
    return false;
  }

  @Override
  public Color getColor() {
    return Color.fromHEX("CD5CAB");
  }

  @Override
  public KelpEffectRating getRating() {
    return KelpEffectRating.POSITIVE;
  }

  @Override
  public boolean isBukkitEffectUnsafe(KelpVersion version) {
    return true;
  }

}
