package de.pxav.kelp.core.entity.util.potion;

import de.pxav.kelp.core.application.KelpVersionTemplate;
import org.bukkit.potion.PotionEffectType;

@KelpVersionTemplate
public abstract class PotionVersionTemplate {

  public abstract PotionEffectType getBukkitPotion(Class<? extends KelpPotionEffect> effect);

  public abstract Class<? extends KelpPotionEffect> getKelpPotion(PotionEffectType effectType);

}
