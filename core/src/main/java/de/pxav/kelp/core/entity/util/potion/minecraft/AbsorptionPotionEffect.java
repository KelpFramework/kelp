package de.pxav.kelp.core.entity.util.potion.minecraft;

import de.pxav.kelp.core.entity.util.potion.KelpPotionEffect;
import de.pxav.kelp.core.inventory.metadata.Color;
import de.pxav.kelp.core.version.KelpVersion;
import org.bukkit.potion.PotionEffectType;

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
public class AbsorptionPotionEffect extends KelpPotionEffect {

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

  /**
   * Gets the amount of extra health points (HP) for the given level.
   * Two health points are equal to 1 heart.
   *
   * @param level The absorption level to get the HP for.
   * @return The HP given by this level
   */
  public int getHealthPointsForLevel(int level) {
    return level * 4;
  }

  /**
   * Gets the amount of extra hearts a player receives when the given
   * level of absorption is applied.
   *
   * @param level The level to get the amount of hearts for.
   * @return The hearts given by this level.
   */
  public int getHeartsForLevel(int level) {
    return level * 2;
  }

}
