package de.pxav.kelp.core.entity.util.potion.minecraft;

import de.pxav.kelp.core.entity.util.potion.KelpEffectRating;
import de.pxav.kelp.core.entity.util.potion.KelpPotionEffectType;
import de.pxav.kelp.core.inventory.metadata.Color;

/**
 * Saturation is an instant status effect that reduces the need for eating.
 * It also prevents death by hunger if the player has no food.
 *
 * The effect instantly replenishes {@code 1FP * level} and {@code 2FP * level}
 * points of saturation.
 *
 * @author pxav
 */
public class SaturationPotionEffect extends KelpPotionEffectType {

  @Override
  public String getName() {
    return "Saturation";
  }

  @Override
  public boolean isInstant() {
    return false;
  }

  @Override
  public Color getColor() {
    return Color.fromHEX("F82421");
  }

  @Override
  public KelpEffectRating getRating() {
    return KelpEffectRating.POSITIVE;
  }

}
