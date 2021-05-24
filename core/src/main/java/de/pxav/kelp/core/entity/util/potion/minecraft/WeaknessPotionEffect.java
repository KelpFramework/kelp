package de.pxav.kelp.core.entity.util.potion.minecraft;

import de.pxav.kelp.core.entity.util.potion.KelpEffectRating;
import de.pxav.kelp.core.entity.util.potion.KelpPotionEffectType;
import de.pxav.kelp.core.entity.util.potion.MinecraftPotion;
import de.pxav.kelp.core.inventory.metadata.Color;
import de.pxav.kelp.core.version.KelpVersion;

/**
 * Melee damage inflicted by the affected entity is reduced by {@code 4HP * level}.
 * Negative levels increase melee damage dealt.
 *
 * @author pxav
 */
@MinecraftPotion(since = KelpVersion.MC_1_8_0)
public class WeaknessPotionEffect extends KelpPotionEffectType {

  /**
   * Calculates how much damage in health points is lost based
   * on the given level of the effect.
   *
   * @param level The level of the weakness effect.
   * @return The amount of damage the melee attack is reduced by.
   */
  public static double getDamageLoss(int level) {
    return 4 * level;
  }

  @Override
  public String getName() {
    return "Weakness";
  }

  @Override
  public boolean isInstant() {
    return false;
  }

  @Override
  public Color getColor() {
    return Color.fromHEX("484D48");
  }

  @Override
  public KelpEffectRating getRating() {
    return KelpEffectRating.NEGATIVE;
  }

  @Override
  public boolean isBukkitEffectUnsafe(KelpVersion version) {
    return true;
  }

}
