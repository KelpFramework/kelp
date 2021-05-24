package de.pxav.kelp.core.entity.util.potion.minecraft;

import de.pxav.kelp.core.entity.util.potion.KelpEffectRating;
import de.pxav.kelp.core.entity.util.potion.KelpPotionEffectType;
import de.pxav.kelp.core.entity.util.potion.MinecraftPotion;
import de.pxav.kelp.core.inventory.metadata.Color;
import de.pxav.kelp.core.version.KelpVersion;

/**
 * Bad Luck, also known as Unluck, is a status effect that decreases
 * the chance of the player getting high-quality loot, contrary to Luck.
 *
 * @author pxav
 */
@MinecraftPotion(since = KelpVersion.MC_1_9_0)
public class BadLuckPotionEffect extends KelpPotionEffectType {

  @Override
  public String getName() {
    return "Bad Luck";
  }

  @Override
  public boolean isInstant() {
    return false;
  }

  @Override
  public Color getColor() {
    return Color.fromHEX("C0A44D");
  }

  @Override
  public KelpEffectRating getRating() {
    return KelpEffectRating.NEGATIVE;
  }

  @Override
  public boolean isBukkitEffectUnsafe(KelpVersion version) {
    return version.isHigherThanOrEqualTo(KelpVersion.MC_1_9_0);
  }

}
