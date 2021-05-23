package de.pxav.kelp.core.entity.util.potion.minecraft;

import de.pxav.kelp.core.entity.util.potion.KelpEffectRating;
import de.pxav.kelp.core.entity.util.potion.KelpPotionEffectType;
import de.pxav.kelp.core.entity.util.potion.MinecraftPotion;
import de.pxav.kelp.core.inventory.metadata.Color;
import de.pxav.kelp.core.version.KelpVersion;

/**
 * Blindness impairs the player's vision as if a thick black fog were surrounding them,
 * with only the immediate area being visible (this means that only a few blocks directly
 * around the player are visible to them).
 * The effect also prevents the player from sprinting or inflicting critical hits.
 * If combined with Night Vision, the screen appears completely black aside from the sun and moon.
 *
 * @author pxav
 */
@MinecraftPotion(since = KelpVersion.MC_1_8_0)
public class BlindnessPotionEffect extends KelpPotionEffectType {

  @Override
  public String getName() {
    return "Blindness";
  }

  @Override
  public boolean isInstant() {
    return false;
  }

  @Override
  public Color getColor() {
    return Color.fromHEX("1F1F23");
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
