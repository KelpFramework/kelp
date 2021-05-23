package de.pxav.kelp.core.entity.util.potion.minecraft;

import de.pxav.kelp.core.entity.util.potion.KelpEffectRating;
import de.pxav.kelp.core.entity.util.potion.KelpPotionEffectType;
import de.pxav.kelp.core.entity.util.potion.MinecraftPotion;
import de.pxav.kelp.core.inventory.metadata.Color;
import de.pxav.kelp.core.npc.MovementSpeed;
import de.pxav.kelp.core.version.KelpVersion;

/**
 * Speed is a status effect that increases an entity's walking speed by 20% multiplied by the effect level.
 * It expands a player's field of view (FOV) accordingly. Negative levels decrease speed.
 * Jumping and falling are also affected by this effect, although not every server can handle the
 * jump speed coming with higher effect levels.
 *
 * @author pxav
 */
@MinecraftPotion(since = KelpVersion.MC_1_8_0)
public class SpeedPotionEffect extends KelpPotionEffectType {

  /**
   * Gets the blocks per second a player can walk with the given
   * level of speed applied.
   *
   * @param level The level to get the walking speed for.
   * @return The amount of blocks a player can walk each second with the given level of speed applied.
   */
  public static double getBlocksPerSecond(MovementSpeed movementSpeed, int level) {
    return movementSpeed.getBlocksPerSecond() * 1.2 * level;
  }

  @Override
  public String getName() {
    return "Speed";
  }

  @Override
  public boolean isInstant() {
    return false;
  }

  @Override
  public Color getColor() {
    return Color.fromHEX("7CAFC6");
  }

  @Override
  public KelpEffectRating getRating() {
    return KelpEffectRating.POSITIVE;
  }

}
