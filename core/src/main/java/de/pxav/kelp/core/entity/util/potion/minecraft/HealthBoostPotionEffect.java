package de.pxav.kelp.core.entity.util.potion.minecraft;

import de.pxav.kelp.core.entity.util.potion.KelpEffectRating;
import de.pxav.kelp.core.entity.util.potion.KelpPotionEffectType;
import de.pxav.kelp.core.inventory.metadata.Color;

/**
 * Health Boost is a status effect that increases a player's (or mob's) maximum health.
 * Adds 4 health points maximum health per level. Unlike Absorption, the added hearts are
 * empty at first, but can be healed through the usual methods
 * such as natural regeneration and the Regeneration and Instant Health effects).
 *
 * When the effect ends, any extra health is lost.
 *
 * @author pxav
 */
public class HealthBoostPotionEffect extends KelpPotionEffectType {

  @Override
  public String getName() {
    return "Health Boost";
  }

  @Override
  public boolean isInstant() {
    return false;
  }

  @Override
  public Color getColor() {
    return Color.fromHEX("F87D23");
  }

  @Override
  public KelpEffectRating getRating() {
    return KelpEffectRating.POSITIVE;
  }

}
