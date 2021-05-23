package de.pxav.kelp.core.entity.util.potion.minecraft;

import de.pxav.kelp.core.entity.util.potion.KelpEffectRating;
import de.pxav.kelp.core.entity.util.potion.KelpPotionEffectType;
import de.pxav.kelp.core.entity.util.potion.MinecraftPotion;
import de.pxav.kelp.core.inventory.metadata.Color;
import de.pxav.kelp.core.version.KelpVersion;

/**
 * Represents the absorption potion effect. Absorption is a status
 * effect that pads the health bar with extra hearts.
 *
 * Absorption adds 4 additional health points per level to the player,
 * displayed as yellow hearts above the normal health bar. If the player takes damage while under this effect,
 * the absorption health points are depleted before the normal health points.
 *
 * @author pxav
 */
@MinecraftPotion(since = KelpVersion.MC_1_8_0)
public class AbsorptionPotionEffect extends KelpPotionEffectType {

  /**
   * Gets the amount of extra health points (HP) for the given level.
   * Two health points are equal to 1 heart.
   *
   * @param level The absorption level to get the HP for.
   * @return The HP given by this level
   */
  public static int getHealthPointsForLevel(int level) {
    return level * 4;
  }

  /**
   * Gets the amount of extra hearts a player receives when the given
   * level of absorption is applied.
   *
   * @param level The level to get the amount of hearts for.
   * @return The hearts given by this level.
   */
  public static int getHeartsForLevel(int level) {
    return level * 2;
  }

  @Override
  public String getName() {
    return "Absorption";
  }

  @Override
  public boolean isInstant() {
    return false;
  }

  @Override
  public Color getColor() {
    return Color.fromHEX("2552A5");
  }

  @Override
  public boolean isBukkitEffectUnsafe(KelpVersion version) {
    return true;
  }

  @Override
  public KelpEffectRating getRating() {
    return KelpEffectRating.POSITIVE;
  }

}
