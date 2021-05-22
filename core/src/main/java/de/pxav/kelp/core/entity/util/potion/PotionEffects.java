package de.pxav.kelp.core.entity.util.potion;

import de.pxav.kelp.core.KelpPlugin;
import de.pxav.kelp.core.entity.util.potion.minecraft.AbsorptionPotionEffect;
import de.pxav.kelp.core.entity.util.potion.minecraft.HastePotionEffect;
import de.pxav.kelp.core.entity.util.potion.minecraft.LevitationEffect;

public abstract class PotionEffects {

  public static final KelpPotionEffectType ABSORPTION = KelpPlugin.getInjector().getInstance(AbsorptionPotionEffect.class);
  public static final KelpPotionEffectType LEVITATION = KelpPlugin.getInjector().getInstance(LevitationEffect.class);
  public static final KelpPotionEffectType HASTE = KelpPlugin.getInjector().getInstance(HastePotionEffect.class);

}
