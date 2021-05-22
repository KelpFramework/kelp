package de.pxav.kelp.core.entity.util.potion;

import de.pxav.kelp.core.KelpPlugin;
import de.pxav.kelp.core.entity.util.potion.minecraft.AbsorptionPotionEffect;
import de.pxav.kelp.core.entity.util.potion.minecraft.HastePotionEffect;
import de.pxav.kelp.core.entity.util.potion.minecraft.LevitationEffect;

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

}
