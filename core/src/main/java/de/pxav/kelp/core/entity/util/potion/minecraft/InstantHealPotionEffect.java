package de.pxav.kelp.core.entity.util.potion.minecraft;

import de.pxav.kelp.core.entity.LivingKelpEntity;
import de.pxav.kelp.core.entity.util.potion.KelpEffectRating;
import de.pxav.kelp.core.entity.util.potion.KelpPotionEffectType;
import de.pxav.kelp.core.entity.util.potion.MinecraftPotion;
import de.pxav.kelp.core.inventory.metadata.Color;
import de.pxav.kelp.core.version.KelpVersion;

/**
 * Instant Health is an instant status effect that increases health
 * for living mobs and damages the undead.
 * Instantly heals {@code 2HP * 2^level}.
 *
 * Undead mobs (including the wither) are damaged as if with Instant Damage, instead.
 * When applied using a lingering potion, the entity is healed every second.
 * Levels 30–32 provide no healing. Levels outside the range 1–32
 * are used modulo 32, making level 33 the same as level 1, etc.
 *
 * Ender dragons are completely immune to this effect.
 *
 * @author pxav
 */
@MinecraftPotion(since = KelpVersion.MC_1_8_0)
public class InstantHealPotionEffect extends KelpPotionEffectType {

  /**
   * Calculates the health gain made for an entity at the given effect level.
   * If the entity is an undead, a negative number will be returned as those
   * entities actually lose health from this effect.
   *
   * @param entity  The entity to calculate the health gain for.
   * @param level   The level with which the effect is applied.
   * @return The total amount of health gain made to the entity in health points.
   */
  public static double getHealthGain(LivingKelpEntity<?> entity, int level) {
    if (level == 30 || level == 31 || level == 32) {
      return 0;
    }
    if (level > 32)  {
      level = level % 32;
    }

    double healthGain = 2 * Math.pow(2, level);

    if (entity.getType().isUndead()) {
      return -healthGain;
    }

    return healthGain;
  }

  @Override
  public String getName() {
    return "Healing";
  }

  @Override
  public boolean isInstant() {
    return true;
  }

  @Override
  public Color getColor() {
    return Color.fromHEX("F82423");
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
