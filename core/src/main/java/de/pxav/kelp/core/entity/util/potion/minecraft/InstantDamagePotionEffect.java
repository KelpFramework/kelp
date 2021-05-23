package de.pxav.kelp.core.entity.util.potion.minecraft;

import de.pxav.kelp.core.common.MathUtils;
import de.pxav.kelp.core.entity.KelpEntityType;
import de.pxav.kelp.core.entity.LivingKelpEntity;
import de.pxav.kelp.core.entity.util.potion.KelpEffectRating;
import de.pxav.kelp.core.entity.util.potion.KelpPotionEffectType;
import de.pxav.kelp.core.inventory.metadata.Color;

/**
 * Instant Damage is an instant status effect that decreases health for living mobs and heals the undead.
 * Instant Damage inflicts magic damage of {@code 3HP * 2^level}. Undead mobs (including the wither)
 * are healed as if with Instant Health instead.
 * When applied using a lingering potion, damage is inflicted every second.
 * As this is magic damage, it can be decreased only via Resistance and Protection.
 *
 * An Instant Damage IV potion is powerful enough to kill a player instantly.
 *
 * @author pxav
 */
public class InstantDamagePotionEffect extends KelpPotionEffectType {

  /**
   * Calculates the health loss made for an entity at the given effect level.
   * If the entity is an undead, a negative number will be returned as those
   * entities actually gain health from this effect. For the calculation of this
   * number the negative value of {}
   *
   * @param entity  The entity to calculate the damage for.
   * @param level   The level with which the effect is applied.
   * @return The total amount of damage made to the entity in health points.
   */
  public static double getHealthLoss(LivingKelpEntity<?> entity, int level) {
    // witches are 85% immune against instant damage
    if (entity.getType() == KelpEntityType.WITCH) {
      if (MathUtils.perCentChance(0.85)) {
        return 0;
      }
    }

    double healthLoss = 3 * Math.pow(2, level);

    if (entity.getType().isUndead()) {
      return -healthLoss;
    }

    return healthLoss;
  }

  @Override
  public String getName() {
    return "Damage";
  }

  @Override
  public boolean isInstant() {
    return true;
  }

  @Override
  public Color getColor() {
    return Color.fromHEX("430A09");
  }

  @Override
  public KelpEffectRating getRating() {
    return KelpEffectRating.NEGATIVE;
  }

}
