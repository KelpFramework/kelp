package de.pxav.kelp.core.entity.util.potion.minecraft;

import de.pxav.kelp.core.entity.util.potion.KelpEffectRating;
import de.pxav.kelp.core.entity.util.potion.KelpPotionEffectType;
import de.pxav.kelp.core.inventory.metadata.Color;

/**
 * Poison inflicts damage over time, reducing the player's health to 1 HP, but cannot kill.
 * The health bar of a player is colored yellow while this effect is applied.
 * The speed with which health is lost, depends on the effect level.
 *
 * @author pxav
 */
public class PoisonPotionEffect extends KelpPotionEffectType {

  @Override
  public String getName() {
    return "Poison";
  }

  @Override
  public boolean isInstant() {
    return false;
  }

  @Override
  public Color getColor() {
    return Color.fromHEX("4E9331");
  }

  @Override
  public KelpEffectRating getRating() {
    return KelpEffectRating.NEGATIVE;
  }

}
