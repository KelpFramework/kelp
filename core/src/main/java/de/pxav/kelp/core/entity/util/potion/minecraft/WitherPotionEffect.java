package de.pxav.kelp.core.entity.util.potion.minecraft;

import de.pxav.kelp.core.entity.util.potion.KelpEffectRating;
import de.pxav.kelp.core.entity.util.potion.KelpPotionEffectType;
import de.pxav.kelp.core.inventory.metadata.Color;

/**
 * Wither is a status effect that inflicts damage over time.
 * Unlike Poison, it can affect undead mobs, and can kill.
 * It is usually difficult for the player to see how much health they have left,
 * as it turns the player's health bar black.
 *
 * @author pxav
 */
public class WitherPotionEffect extends KelpPotionEffectType {

  @Override
  public String getName() {
    return "Wither";
  }

  @Override
  public boolean isInstant() {
    return false;
  }

  @Override
  public Color getColor() {
    return Color.fromHEX("352A27");
  }

  @Override
  public KelpEffectRating getRating() {
    return KelpEffectRating.NEGATIVE;
  }

}
