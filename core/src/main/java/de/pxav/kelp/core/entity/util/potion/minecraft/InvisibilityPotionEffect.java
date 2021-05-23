package de.pxav.kelp.core.entity.util.potion.minecraft;

import de.pxav.kelp.core.entity.KelpEntity;
import de.pxav.kelp.core.entity.util.potion.KelpEffectRating;
import de.pxav.kelp.core.entity.util.potion.KelpPotionEffect;
import de.pxav.kelp.core.entity.util.potion.KelpPotionEffectType;
import de.pxav.kelp.core.inventory.metadata.Color;
import de.pxav.kelp.core.player.KelpPlayer;

/**
 * Invisibility is a status effect that turns entities invisible.
 * Applying higher levels does not make a difference, however the duration
 * can be extended when applied as an {@link KelpPotionEffect effect}.
 *
 * Please note that this effect may not cause complete invisibility as
 * only the entity itself is hidden. Particle effects (e. g. from a potion),
 * armor or arrows are still visible for other players. To completely hide
 * a player, use {@link de.pxav.kelp.core.player.KelpPlayer#hidePlayer(KelpPlayer)}
 * or {@link KelpEntity#remove()} for entities.
 *
 * @author pxav
 */
public class InvisibilityPotionEffect extends KelpPotionEffectType {

  @Override
  public String getName() {
    return "Invisibility";
  }

  @Override
  public boolean isInstant() {
    return false;
  }

  @Override
  public Color getColor() {
    return Color.fromHEX("7F8392");
  }

  @Override
  public KelpEffectRating getRating() {
    return KelpEffectRating.POSITIVE;
  }

}
