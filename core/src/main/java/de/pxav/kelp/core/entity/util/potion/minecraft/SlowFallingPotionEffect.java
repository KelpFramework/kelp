package de.pxav.kelp.core.entity.util.potion.minecraft;

import de.pxav.kelp.core.entity.util.potion.KelpEffectRating;
import de.pxav.kelp.core.entity.util.potion.KelpPotionEffectType;
import de.pxav.kelp.core.entity.util.potion.MinecraftPotion;
import de.pxav.kelp.core.inventory.metadata.Color;
import de.pxav.kelp.core.version.KelpVersion;

/**
 * The affected entity falls at a much slower rate than normal, and is immune to fall damage.
 * However, the entity still takes damage from using an ender pearl.
 * In addition, the entity cannot turn farmland into dirt by jumping on it.
 *
 * Higher levels do not have an impact on this effect.
 *
 * @author pxav
 */
@MinecraftPotion(since = KelpVersion.MC_1_13_0)
public class SlowFallingPotionEffect extends KelpPotionEffectType {

  @Override
  public String getName() {
    return "Slow Falling";
  }

  @Override
  public boolean isInstant() {
    return false;
  }

  @Override
  public Color getColor() {
    return Color.fromHEX("F7F8E0");
  }

  @Override
  public KelpEffectRating getRating() {
    return KelpEffectRating.POSITIVE;
  }

}
