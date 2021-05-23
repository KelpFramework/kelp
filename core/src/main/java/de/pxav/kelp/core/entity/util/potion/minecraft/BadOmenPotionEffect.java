package de.pxav.kelp.core.entity.util.potion.minecraft;

import de.pxav.kelp.core.entity.util.potion.KelpEffectRating;
import de.pxav.kelp.core.entity.util.potion.KelpPotionEffectType;
import de.pxav.kelp.core.entity.util.potion.MinecraftPotion;
import de.pxav.kelp.core.inventory.metadata.Color;
import de.pxav.kelp.core.version.KelpVersion;

/**
 * Bad Omen is a status effect that causes a raid to appear
 * when an afflicted player enters a village.
 *
 * @author pxav
 */
@MinecraftPotion(since = KelpVersion.MC_1_14_0)
public class BadOmenPotionEffect extends KelpPotionEffectType {

  @Override
  public String getName() {
    return "Bad Omen";
  }

  @Override
  public boolean isInstant() {
    return false;
  }

  @Override
  public Color getColor() {
    return Color.fromHEX("0b6138");
  }

  @Override
  public KelpEffectRating getRating() {
    return KelpEffectRating.MIXED;
  }

}
