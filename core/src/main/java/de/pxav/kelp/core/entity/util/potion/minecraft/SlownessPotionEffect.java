package de.pxav.kelp.core.entity.util.potion.minecraft;

import de.pxav.kelp.core.entity.util.potion.KelpEffectRating;
import de.pxav.kelp.core.entity.util.potion.KelpPotionEffectType;
import de.pxav.kelp.core.entity.util.potion.MinecraftPotion;
import de.pxav.kelp.core.inventory.metadata.Color;
import de.pxav.kelp.core.npc.MovementSpeed;
import de.pxav.kelp.core.version.KelpVersion;

/**
 * Slowness decreases walking speed by {@code 15% * level} and contracts the player's field of view accordingly.
 * Negative levels of Slowness increase walking speed. However, sprinting and jumping is barely affected,
 * unlike with the {@link SpeedPotionEffect}.
 *
 * @author pxav
 */
@MinecraftPotion(since = KelpVersion.MC_1_8_0)
public class SlownessPotionEffect extends KelpPotionEffectType {

  /**
   * Gets the blocks per second a player can walk with the given
   * level of slowness applied.
   *
   * @param level The level to get the walking speed for.
   * @return The amount of blocks a player can walk each second with the given level of slowness applied.
   */
  public static double getBlocksPerSecond(MovementSpeed movementSpeed, int level) {
    // TODO: Add MovementSpeed.JUMPING!
    return movementSpeed.getBlocksPerSecond() * 1.15 * level;
  }

  @Override
  public String getName() {
    return "Slowness";
  }

  @Override
  public boolean isInstant() {
    return false;
  }

  @Override
  public Color getColor() {
    return Color.fromHEX("5A6C81");
  }

  @Override
  public KelpEffectRating getRating() {
    return KelpEffectRating.NEGATIVE;
  }

  @Override
  public boolean isBukkitEffectUnsafe(KelpVersion version) {
    return true;
  }

}
