package de.pxav.kelp.core.entity.util.potion.minecraft;

import de.pxav.kelp.core.entity.util.potion.KelpEffectRating;
import de.pxav.kelp.core.entity.util.potion.KelpPotionEffectType;
import de.pxav.kelp.core.entity.util.potion.MinecraftPotion;
import de.pxav.kelp.core.inventory.metadata.Color;
import de.pxav.kelp.core.version.KelpVersion;

/**
 * Dolphin's Grace is a status effect that increases the player's swimming
 * speed when a dolphin is nearby and following the player.
 *
 * @author pxav
 */
@MinecraftPotion(since = KelpVersion.MC_1_13_0)
public class DolphinsGracePotionEffect extends KelpPotionEffectType {

  @Override
  public String getName() {
    return "Dolphin's Grace";
  }

  @Override
  public boolean isInstant() {
    return false;
  }

  @Override
  public Color getColor() {
    return Color.fromHEX("88A3BE");
  }

  @Override
  public KelpEffectRating getRating() {
    return KelpEffectRating.POSITIVE;
  }

  @Override
  public boolean isBukkitEffectUnsafe(KelpVersion version) {
    return version.isHigherThanOrEqualTo(KelpVersion.MC_1_13_0);
  }

}
