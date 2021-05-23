package de.pxav.kelp.core.entity.util.potion.minecraft;

import de.pxav.kelp.core.entity.util.potion.KelpEffectRating;
import de.pxav.kelp.core.entity.util.potion.KelpPotionEffectType;
import de.pxav.kelp.core.entity.util.potion.MinecraftPotion;
import de.pxav.kelp.core.inventory.metadata.Color;
import de.pxav.kelp.core.version.KelpVersion;

/**
 * Mining Fatigue reduces mining and attack speed, opposite to the
 * {@link HastePotionEffect Haste} effect. Unlike the Haste effect,
 * this effect actually reduces the attack speed and not only its animation.
 *
 * @author pxav
 */
@MinecraftPotion(since = KelpVersion.MC_1_8_0)
public class MiningFatiguePotionEffect extends KelpPotionEffectType {

  /**
   * Gets the attack speed in per cent of the player's original attack speed.
   * The normal attack speed is 100% ({@code 1}), therefore {@code 0.7} would be
   * a {@code 30%} decrease in attack speed.
   *
   * @param level The level this effect is applied with.
   * @return The attack speed modifier of the player based on the given effect level.
   */
  public static double getAttackSpeedModifier(int level) {
    return 1 - (0.1 * level);
  }

  @Override
  public String getName() {
    return "Mining Fatigue";
  }

  @Override
  public boolean isInstant() {
    return false;
  }

  @Override
  public Color getColor() {
    return Color.fromHEX("4A4217");
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
