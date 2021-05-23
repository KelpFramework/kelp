package de.pxav.kelp.core.entity.util.potion.minecraft;

import de.pxav.kelp.core.entity.util.potion.KelpEffectRating;
import de.pxav.kelp.core.entity.util.potion.KelpPotionEffectType;
import de.pxav.kelp.core.entity.util.potion.MinecraftPotion;
import de.pxav.kelp.core.inventory.metadata.Color;
import de.pxav.kelp.core.version.KelpVersion;

/**
 * Strength is an effect which increases attack power.
 *
 * Increases melee damage by {@code 3HP × level}.
 * Negative levels decrease melee damage, with attacks being ignored entirely
 * if damage would be 0 or lower.
 *
 * @author pxav
 */
@MinecraftPotion(since = KelpVersion.MC_1_8_0)
public class StrengthPotionEffect extends KelpPotionEffectType {

  /**
   * Calculates the increased/decreased total attack damage made by the given
   * effect level and the original attack damage.
   *
   * @param originalAttackDamage The original attack damage in health points
   *                             ({@code heart amount * 2}).
   * @param level                The level of the potion effect.
   * @return The amount of damage made with the given effect level and
   *         original attack damage in health points.
   */
  public static double getDamageIncrease(double originalAttackDamage, int level) {
    if (level == 0) {
      return originalAttackDamage;
    }
    double attackDamage = originalAttackDamage + (3 * level);
    return attackDamage > 0 ? attackDamage : 0;
  }

  // Todo: getDamageIncrease() method, where you can pass a KelpItem
  // Todo: Kelp then automatically checks the material and enchantments to
  // Todo: determine the original attack damage

  @Override
  public String getName() {
    return "Strength";
  }

  @Override
  public boolean isInstant() {
    return false;
  }

  @Override
  public Color getColor() {
    return Color.fromHEX("932423");
  }

  @Override
  public KelpEffectRating getRating() {
    return KelpEffectRating.POSITIVE;
  }

}
