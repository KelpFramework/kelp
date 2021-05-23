package de.pxav.kelp.core.entity.util.potion;

import com.google.common.collect.Lists;
import de.pxav.kelp.core.KelpPlugin;
import de.pxav.kelp.core.KelpServer;
import de.pxav.kelp.core.entity.util.potion.minecraft.AbsorptionPotionEffect;
import de.pxav.kelp.core.entity.util.potion.minecraft.HastePotionEffect;
import de.pxav.kelp.core.entity.util.potion.minecraft.LevitationEffect;
import de.pxav.kelp.core.version.KelpVersion;

import java.util.Collections;
import java.util.List;

/**
 * This class holds some constant values for quick access to some potion
 * effect types needed to for example create a new {@link KelpPotionEffect} instance,
 * which can then be {@link de.pxav.kelp.core.entity.LivingKelpEntity#addPotionEffect(KelpPotionEffect) applied to an entity}.
 *
 * Please note that this class is not an enum and its values are therefore not comparable
 * using the {@code ==} operator. If you want to compare effect types, use
 * {@code PotionEffects.ABSORPTION.equals(anotherPotionEffectInstance)} instead.
 *
 * @author pxav
 */
public abstract class PotionEffects {

  // default minecraft potion types.
  public static final KelpPotionEffectType ABSORPTION = KelpPlugin.getInjector().getInstance(AbsorptionPotionEffect.class);
  public static final KelpPotionEffectType LEVITATION = KelpPlugin.getInjector().getInstance(LevitationEffect.class);
  public static final KelpPotionEffectType HASTE = KelpPlugin.getInjector().getInstance(HastePotionEffect.class);

  /**
   * Picks a random potion effect type from all existing ones
   * at the current server version. This method uses values
   * returned from {@link #values()} in the background.
   *
   * @return A random potion effect type available at the current server version.
   */
  public static KelpPotionEffectType randomEffectType() {
    final List<KelpPotionEffectType> types = values();
    Collections.shuffle(types);
    return types.get(0);
  }

  /**
   * Picks a random potion effect type from existing ones at the
   * current server version. Only potion types with the given rating
   * are returned, meaning that if you want to have a positive potion
   * effect type, {@link KelpEffectRating#POSITIVE} has to be passed.
   *
   * If you don't care about the effect's rating, use {@link #randomEffectType()}
   * instead.
   *
   * @param rating The rating of the effect.
   * @return
   */
  public static KelpPotionEffectType randomEffectType(KelpEffectRating rating) {
    final List<KelpPotionEffectType> types = values();
    do {
      Collections.shuffle(types);
    } while (types.get(0).getRating() != rating);
    return types.get(0);
  }

  public static KelpPotionEffectType valueOf(String fieldName) {
    switch (fieldName) {
      case "ABSORPTION": return ABSORPTION;
      case "LEVITATION": return LEVITATION;
      case "HASTE": return HASTE;
    }
    return PotionEffects.ABSORPTION;
  }

  /**
   * Returns all potion effect types existing on the current server version.
   * So if you call this method on a 1.8 server, {@code LEVITATION} will be excluded,
   * while it is included in 1.9+.
   *
   * @return A list of all potion effect types available at the current server version.
   */
  public static List<KelpPotionEffectType> values() {
    List<KelpPotionEffectType> output = Lists.newArrayList(
      ABSORPTION,
      HASTE
    );

    if (KelpServer.getVersion().isHigherThanOrEqualTo(KelpVersion.MC_1_9_0)) {
      output.add(LEVITATION);
    }

    return output;
  }

}
