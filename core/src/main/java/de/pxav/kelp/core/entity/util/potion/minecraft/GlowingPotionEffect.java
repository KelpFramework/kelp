package de.pxav.kelp.core.entity.util.potion.minecraft;

import de.pxav.kelp.core.entity.util.potion.KelpEffectRating;
import de.pxav.kelp.core.entity.util.potion.KelpPotionEffectType;
import de.pxav.kelp.core.entity.util.potion.MinecraftPotion;
import de.pxav.kelp.core.inventory.metadata.Color;
import de.pxav.kelp.core.version.KelpVersion;

/**
 * The Glowing effect causes entities to glow with an outline that can be seen through blocks and entities.
 * This outline is white by default, but can be set to display other colors if the entity is part of a team.
 * The outline displays around any holes in a mob's texture or model, though only when that part of the model
 * can be seen all the way through. If multiple entities with glowing are near each other,
 * the outlines merge to prevent overlapping.
 *
 * @author pxav
 */
@MinecraftPotion(since = KelpVersion.MC_1_9_0)
public class GlowingPotionEffect extends KelpPotionEffectType {

  @Override
  public String getName() {
    return "Glowing";
  }

  @Override
  public boolean isInstant() {
    return false;
  }

  @Override
  public Color getColor() {
    return Color.fromHEX("94A061");
  }

  @Override
  public KelpEffectRating getRating() {
    return KelpEffectRating.MIXED;
  }

}
