package de.pxav.kelp.core.entity.util.potion;

import de.pxav.kelp.core.KelpServer;
import de.pxav.kelp.core.entity.util.potion.minecraft.*;
import de.pxav.kelp.core.inventory.metadata.Color;
import de.pxav.kelp.core.player.KelpPlayer;
import de.pxav.kelp.core.version.KelpVersion;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * Represents an abstract potion effect a player can get by drinking a potion for example.
 *
 * Every potion effect type has to extend from this class to define the individual properties.
 * But please note that it is currently not possible to add custom effects as they are hard coded
 * into the minecraft client and are therefore not modifiable by the server, unlike
 * {@link de.pxav.kelp.core.inventory.enchant.KelpEnchantment enchantments}. If this feature is ever added
 * to minecraft, you will be able to create custom effects by extending this class as well.
 *
 * @author pxav
 */
public abstract class KelpPotionEffectType {

  /**
   * Gets the default name of the effect. Please note that the actual name displayed
   * to the player may vary as this is dependent on the player's client language.
   * Names returned by this method are always in english.
   *
   * @return
   */
  public abstract String getName();

  /**
   * Determines whether the effect is instant. An instant is an effect that applies
   * instantly and does not last for several ticks. Once consumed, the player will
   * have no long-term effects. An example would be the instant damage or instant healing
   * potion by minecraft.
   *
   * @return {@code true} if the effect is instant.
   */
  public abstract boolean isInstant();

  /**
   * Gets the color used by the particles of this effect.
   *
   * @return The color of the particles used by this effect.
   */
  public abstract Color getColor();

  /**
   * Gets the rating of this effect. The rating gives information
   * on whether getting this effect is considered positive, negative or mixed.
   * Damage for example would be negative, while regeneration would be positive.
   * Mixed effects would be effects that give you positive abilities at the cost
   * of a slower speed for example.
   *
   * @return The effect rating of this potion effect.
   */
  public abstract KelpEffectRating getRating();

  /**
   * If custom potion effects are ever added to minecraft, this method can be used to
   * define what should happen with a player when the effect is applied to them.
   *
   * @param player The player who consumed the potion effect and to whom the effects should be applied.
   */
  public void onConsume(KelpPlayer player) {}

  /**
   * Checks if this effect is a default potion effect offered by minecraft/bukkit.
   * As of now, every effect will return {@code true} on this as custom potion effects
   * are not yet possible in minecraft.
   *
   * @return {@code true} if the effect is a default effect.
   */
  public final boolean isBukkitEffect() {
    return isBukkitEffectUnsafe(KelpServer.getVersion());
  }

  public boolean isBukkitEffectUnsafe(KelpVersion version) {
    return false;
  }

  @Override
  public boolean equals(Object other) {
    if (!(other instanceof KelpPotionEffectType)) {
      return false;
    }

    KelpPotionEffectType otherEffect = (KelpPotionEffectType) other;

    return otherEffect.getName().equals(getName())
      && otherEffect.isInstant() == isInstant()
      && otherEffect.getColor().equals(getColor());
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(37, 17)
      .append(getName())
      .append(getColor())
      .append(getRating())
      .toHashCode();
  }

}
