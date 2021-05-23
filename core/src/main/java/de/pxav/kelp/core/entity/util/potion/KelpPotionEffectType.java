package de.pxav.kelp.core.entity.util.potion;

import de.pxav.kelp.core.KelpServer;
import de.pxav.kelp.core.entity.LivingKelpEntity;
import de.pxav.kelp.core.inventory.metadata.Color;
import de.pxav.kelp.core.version.KelpVersion;
import net.minecraft.server.v1_16_R3.CriterionTriggerLevitation;
import net.minecraft.server.v1_16_R3.EntityShulkerBullet;
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
   * define what should happen with an entity when the effect is applied to them.
   *
   * @param entity The entity who consumed the potion effect and to whom the effects should be applied.
   * @param effectInfo Other information about the effect such as the duration or level. This also includes if
   *                   particles should be displayed.
   */
  public void onConsume(LivingKelpEntity<?> entity, KelpPotionEffect effectInfo) {}

  /**
   * This method is triggered when the effect is removed from an entity.
   * However, this only applies for effects that do not natively exist in the current
   * server version and are therefore emulated by Kelp. This is therefore not triggered
   * when the effect is removed via the official {@code /effect} command, but only
   * when drinking milk or using the {@link LivingKelpEntity#removePotionEffect(KelpPotionEffectType)}
   * method.
   *
   * @param entity The entity from which the effect has been removed.
   */
  public void onRemove(LivingKelpEntity<?> entity) {}

  /**
   * Checks if this potion effect is emulated for the current server version.
   * So all potions that exist natively for the current version, will return
   * false. Only potions that can be emulated without modifying too much of
   * the gameplay experience will return {@code true} on this. If this returns
   * true, you can apply the potion effect even if it does not exist on your
   * server version.
   *
   * @return {@code true} if the potion effect type is emulated by Kelp.
   */
  public boolean isEmulated() {
    return false;
  }

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
