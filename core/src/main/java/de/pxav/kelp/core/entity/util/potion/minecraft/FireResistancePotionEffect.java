package de.pxav.kelp.core.entity.util.potion.minecraft;

import de.pxav.kelp.core.entity.util.potion.KelpEffectRating;
import de.pxav.kelp.core.entity.util.potion.KelpPotionEffectType;
import de.pxav.kelp.core.inventory.metadata.Color;

/**
 * Grants immunity to damage from fire, blaze fireballs, fire charges,
 * magma blocks, and lava, but not ghast fireballs and blaze touch attacks.
 * Negates the fire damage from bows enchanted with Flame and swords enchanted
 * with Fire Aspect, but does not affect the attacks themselves.
 * The fog effect under lava is also mitigated somewhat.
 * Players also see better under lava when they have the Fire Resistance effect.
 *
 * @author pxav
 */
public class FireResistancePotionEffect extends KelpPotionEffectType {

  @Override
  public String getName() {
    return "Fire Resistance";
  }

  @Override
  public boolean isInstant() {
    return false;
  }

  @Override
  public Color getColor() {
    return Color.fromHEX("E49A3A");
  }

  @Override
  public KelpEffectRating getRating() {
    return KelpEffectRating.POSITIVE;
  }

}
