package de.pxav.kelp.core.entity.util.potion;

import de.pxav.kelp.core.application.KelpVersionTemplate;
import de.pxav.kelp.core.entity.KelpEntity;
import de.pxav.kelp.core.entity.LivingKelpEntity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * This class allows you to execute version-dependent operations
 * on potion effects such as converting bukkit and kelp effects.
 *
 * This does not implement potion effects that are unavailable in
 * some versions, please look at the individual potion effect classes
 * in this case.
 *
 * @author pxav
 */
@KelpVersionTemplate
public abstract class PotionVersionTemplate {

  /**
   * Gets the bukkit potion effect type from a given kelp effect type.
   *
   * This method can obviously only return the potion types that are available
   * in the bukkit version you are running, so if you request {@link PotionEffectType#LEVITATION}
   * on a 1.8 server, {@code null} will be returned as there is no equivalent.
   *
   * @param effect The kelp effect type you want to get the bukkit equivalent of.
   * @return The bukkit potion effect type equivalent to the given kelp effect type.
   */
  public abstract PotionEffectType getBukkitPotion(KelpPotionEffectType effect);

  /**
   * Applies the given {@link KelpPotionEffect potion effect} to the given entity.
   * Not every effect will have the same consequences for all entities. Some entities might
   * be immune against certain effects or can be affected differently than expected
   * (e. g. Zombies are damaged by a heal potion).
   *
   * If the requested potion effect type does not exist, the Kelp implementation of
   * this effect will be called to emulate the effect.
   *
   * @param entity        The entity to apply the effect to.
   * @param potionEffect  The {@link KelpPotionEffect potion effect} to apply to the entity
   *                      including all information such as duration or level of the effect.
   */
  public abstract void applyTo(LivingKelpEntity<?> entity, KelpPotionEffect potionEffect);

  /**
   * Converts the given bukkit potion effect to a {@link KelpPotionEffect}.
   * This is useful when getting all effects of an entity for example.
   *
   * @param effect The bukkit effect to convert.
   * @return A new instance of {@link KelpPotionEffect} containing the data equivalent to the bukkit potion effect.
   */
  public abstract KelpPotionEffect fetchEffect(PotionEffect effect);

  /**
   * Converts the given bukkit potion effect type to the equivalent kelp effect type.
   *
   * @param effectType The bukkit effect type to convert.
   * @return The {@link KelpPotionEffectType} equivalent to the given bukkit effect.
   */
  public abstract KelpPotionEffectType getKelpPotion(PotionEffectType effectType);

}
