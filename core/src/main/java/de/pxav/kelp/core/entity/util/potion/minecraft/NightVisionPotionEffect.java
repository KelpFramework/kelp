package de.pxav.kelp.core.entity.util.potion.minecraft;

import de.pxav.kelp.core.entity.util.potion.KelpEffectRating;
import de.pxav.kelp.core.entity.util.potion.KelpPotionEffectType;
import de.pxav.kelp.core.inventory.metadata.Color;

/**
 * Night vision greatly increases the brightness, so the player can easily see in a light level of 0.
 * But this effect is not strong enough to look the same as light level 15. That's why the player
 * can still visually distinguish between light levels when this effect is applied.
 *
 * @author pxav
 */
public class NightVisionPotionEffect extends KelpPotionEffectType {

  @Override
  public String getName() {
    return "Night Vision";
  }

  @Override
  public boolean isInstant() {
    return false;
  }

  @Override
  public Color getColor() {
    return Color.fromHEX("1F1FA1");
  }

  @Override
  public KelpEffectRating getRating() {
    return KelpEffectRating.POSITIVE;
  }

}
