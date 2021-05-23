package de.pxav.kelp.core.entity.util.potion.minecraft;

import de.pxav.kelp.core.entity.util.potion.KelpEffectRating;
import de.pxav.kelp.core.entity.util.potion.KelpPotionEffectType;
import de.pxav.kelp.core.inventory.metadata.Color;

/**
 * Nausea causes the player's vision to warp and wobble, with the distortion ramping
 * up and down at the start and end of the effect. Although Nausea can be given in the
 * normal range of levels, the level has no impact - it does not increase the distortion.
 *
 * The intensity of this effect can be configured in the client's video settings.
 *
 * @author pxav
 */
public class NauseaPotionEffect extends KelpPotionEffectType {

  @Override
  public String getName() {
    return "Nausea";
  }

  @Override
  public boolean isInstant() {
    return false;
  }

  @Override
  public Color getColor() {
    return Color.fromHEX("551D4A");
  }

  @Override
  public KelpEffectRating getRating() {
    return KelpEffectRating.NEGATIVE;
  }

}
