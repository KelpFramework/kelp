package de.pxav.kelp.core.entity.util.potion.minecraft;

import de.pxav.kelp.core.entity.util.potion.KelpEffectRating;
import de.pxav.kelp.core.entity.util.potion.KelpPotionEffectType;
import de.pxav.kelp.core.inventory.metadata.Color;

/**
 * Jump Boost is a status effect that temporarily increases the jump height of the player.
 *
 * Jump Boost allows the player to jump higher than the normal 1.25 blocks.
 * Each level adds 50% to the jump height. It also reduces fall damage by 2 health points each level.
 * If the level is high enough, fall damage can be avoided completely.
 *
 * @author pxav
 */
public class JumpBoostPotionEffect extends KelpPotionEffectType {

  /**
   * Calculates the amount of blocks a player can jump along the Y axis
   * with the given level of jump boost applied.
   *
   * @param level The jump boost level to calculate the height for.
   * @return The jump height of a player with the given jump boost level.
   */
  public static double getJumpHeight(int level) {
    return 1.25 * Math.pow(1.5, level);
  }

  @Override
  public String getName() {
    return "Jump Boost";
  }

  @Override
  public boolean isInstant() {
    return false;
  }

  @Override
  public Color getColor() {
    return Color.fromHEX("22FF4C");
  }

  @Override
  public KelpEffectRating getRating() {
    return KelpEffectRating.POSITIVE;
  }

}
