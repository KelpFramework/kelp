package de.pxav.kelp.core.entity.util.potion.minecraft;

import de.pxav.kelp.core.entity.util.potion.KelpEffectRating;
import de.pxav.kelp.core.entity.util.potion.KelpPotionEffectType;
import de.pxav.kelp.core.entity.util.potion.MinecraftPotion;
import de.pxav.kelp.core.inventory.metadata.Color;
import de.pxav.kelp.core.version.KelpVersion;

@MinecraftPotion(since = KelpVersion.MC_1_9_0)
public class LevitationEffect extends KelpPotionEffectType {

  @Override
  public String getName() {
    return "Levitation";
  }

  @Override
  public boolean isInstant() {
    return false;
  }

  @Override
  public Color getColor() {
    return Color.fromHEX("CEFFFF");
  }

  @Override
  public KelpEffectRating getRating() {
    return KelpEffectRating.NEGATIVE;
  }

  @Override
  public boolean isEmulated() {
    return true;
  }

  @Override
  public boolean isBukkitEffectUnsafe(KelpVersion version) {
    return version.isHigherThanOrEqualTo(KelpVersion.MC_1_9_0);
  }

}
