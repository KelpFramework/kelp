package de.pxav.kelp.core.entity.util.potion.minecraft;

import de.pxav.kelp.core.entity.util.potion.KelpEffectRating;
import de.pxav.kelp.core.entity.util.potion.KelpPotionEffectType;
import de.pxav.kelp.core.inventory.metadata.Color;

/**
 * Resistance reduces incoming damage from all sources except starvation,
 * the void, and {@code /kill} by {@code 20% * level}.
 *
 * Level 5 gives the player full immunity to all damage, while the exceptions
 * from above persist.
 *
 * @author pxav
 */
public class ResistancePotionEffect extends KelpPotionEffectType {

  @Override
  public String getName() {
    return "Resistance";
  }

  @Override
  public boolean isInstant() {
    return false;
  }

  @Override
  public Color getColor() {
    return Color.fromHEX("99453A");
  }

  @Override
  public KelpEffectRating getRating() {
    return KelpEffectRating.POSITIVE;
  }

}
