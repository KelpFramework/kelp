package de.pxav.kelp.core.entity.util.potion.minecraft;

import de.pxav.kelp.core.entity.util.potion.KelpEffectRating;
import de.pxav.kelp.core.entity.util.potion.KelpPotionEffectType;
import de.pxav.kelp.core.entity.util.potion.MinecraftPotion;
import de.pxav.kelp.core.inventory.metadata.Color;
import de.pxav.kelp.core.version.KelpVersion;

/**
 * Luck is a status effect that makes it more likely to receive better
 * loot from certain loot tables in generated structures.
 *
 * @author pxav
 */
@MinecraftPotion(since = KelpVersion.MC_1_9_0)
public class LuckPotionEffect extends KelpPotionEffectType {

  @Override
  public String getName() {
    return "Luck";
  }

  @Override
  public boolean isInstant() {
    return false;
  }

  @Override
  public Color getColor() {
    return Color.fromHEX("339900");
  }

  @Override
  public KelpEffectRating getRating() {
    return KelpEffectRating.POSITIVE;
  }

}
