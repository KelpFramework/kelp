package de.pxav.kelp.core.entity.util.potion;

import com.google.common.collect.Lists;
import de.pxav.kelp.core.KelpPlugin;
import de.pxav.kelp.core.KelpServer;
import de.pxav.kelp.core.entity.util.potion.minecraft.*;
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
  public static final KelpPotionEffectType BAD_LUCK = KelpPlugin.getInjector().getInstance(BadLuckPotionEffect.class);
  public static final KelpPotionEffectType BAD_OMEN = KelpPlugin.getInjector().getInstance(BadOmenPotionEffect.class);
  public static final KelpPotionEffectType BLINDNESS = KelpPlugin.getInjector().getInstance(BlindnessPotionEffect.class);
  public static final KelpPotionEffectType CONDUIT_POWER = KelpPlugin.getInjector().getInstance(ConduitPowerPotionEffect.class);
  public static final KelpPotionEffectType DOLPHINS_GRACE = KelpPlugin.getInjector().getInstance(DolphinsGracePotionEffect.class);
  public static final KelpPotionEffectType FIRE_RESISTANCE = KelpPlugin.getInjector().getInstance(FireResistancePotionEffect.class);
  public static final KelpPotionEffectType GLOWING = KelpPlugin.getInjector().getInstance(GlowingPotionEffect.class);
  public static final KelpPotionEffectType HASTE = KelpPlugin.getInjector().getInstance(HastePotionEffect.class);
  public static final KelpPotionEffectType HEALTH_BOOST = KelpPlugin.getInjector().getInstance(HealthBoostPotionEffect.class);
  public static final KelpPotionEffectType HERO_OF_THE_VILLAGE = KelpPlugin.getInjector().getInstance(HeroOfTheVillagePotionEffect.class);
  public static final KelpPotionEffectType HUNGER = KelpPlugin.getInjector().getInstance(HungerPotionEffect.class);
  public static final KelpPotionEffectType INSTANT_DAMAGE = KelpPlugin.getInjector().getInstance(InstantDamagePotionEffect.class);
  public static final KelpPotionEffectType INSTANT_HEAL = KelpPlugin.getInjector().getInstance(InstantHealPotionEffect.class);
  public static final KelpPotionEffectType INVISIBILITY = KelpPlugin.getInjector().getInstance(InvisibilityPotionEffect.class);
  public static final KelpPotionEffectType JUMP_BOOST = KelpPlugin.getInjector().getInstance(JumpBoostPotionEffect.class);
  public static final KelpPotionEffectType LEVITATION = KelpPlugin.getInjector().getInstance(LevitationEffect.class);
  public static final KelpPotionEffectType LUCK = KelpPlugin.getInjector().getInstance(LuckPotionEffect.class);
  public static final KelpPotionEffectType MINING_FATIGUE = KelpPlugin.getInjector().getInstance(MiningFatiguePotionEffect.class);
  public static final KelpPotionEffectType NAUSEA = KelpPlugin.getInjector().getInstance(NauseaPotionEffect.class);
  public static final KelpPotionEffectType NIGHT_VISION = KelpPlugin.getInjector().getInstance(NightVisionPotionEffect.class);
  public static final KelpPotionEffectType POISON = KelpPlugin.getInjector().getInstance(PoisonPotionEffect.class);
  public static final KelpPotionEffectType REGENERATION = KelpPlugin.getInjector().getInstance(RegenerationPotionEffect.class);
  public static final KelpPotionEffectType RESISTANCE = KelpPlugin.getInjector().getInstance(ResistancePotionEffect.class);
  public static final KelpPotionEffectType SATURATION = KelpPlugin.getInjector().getInstance(SaturationPotionEffect.class);
  public static final KelpPotionEffectType SLOW_FALLING = KelpPlugin.getInjector().getInstance(SlowFallingPotionEffect.class);
  public static final KelpPotionEffectType SLOWNESS = KelpPlugin.getInjector().getInstance(SlownessPotionEffect.class);
  public static final KelpPotionEffectType SPEED = KelpPlugin.getInjector().getInstance(SpeedPotionEffect.class);
  public static final KelpPotionEffectType STRENGTH = KelpPlugin.getInjector().getInstance(StrengthPotionEffect.class);
  public static final KelpPotionEffectType WATER_BREATHING = KelpPlugin.getInjector().getInstance(WaterBreathingPotionEffect.class);
  public static final KelpPotionEffectType WEAKNESS = KelpPlugin.getInjector().getInstance(WeaknessPotionEffect.class);
  public static final KelpPotionEffectType WITHER = KelpPlugin.getInjector().getInstance(WitherPotionEffect.class);

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
