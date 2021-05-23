package de.pxav.kelp.core.entity.util.potion.minecraft;

import de.pxav.kelp.core.entity.util.potion.KelpEffectRating;
import de.pxav.kelp.core.entity.util.potion.KelpPotionEffectType;
import de.pxav.kelp.core.inventory.metadata.Color;

/**
 * Hunger increases food exhaustion by {@code 0.005 * level} per game tick
 * (removes 1 food saturation point every {@code 40 / level seconds}).
 * It also turns the hunger bar a yellow-greenish color.
 * Negative levels decrease food exhaustion, although they do not increase saturation or the hunger bar.
 *
 * The status effect does not decrease hunger level on Peaceful mode,
 * although it does re-color the hunger bar.
 *
 * @author pxav
 */
public class HungerPotionEffect extends KelpPotionEffectType {

  @Override
  public String getName() {
    return "Hunger";
  }

  @Override
  public boolean isInstant() {
    return false;
  }

  @Override
  public Color getColor() {
    return Color.fromHEX("587653");
  }

  @Override
  public KelpEffectRating getRating() {
    return KelpEffectRating.NEGATIVE;
  }

}
