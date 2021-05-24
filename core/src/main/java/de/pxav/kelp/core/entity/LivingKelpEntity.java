package de.pxav.kelp.core.entity;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import de.pxav.kelp.core.entity.type.general.DamageableEntity;
import de.pxav.kelp.core.entity.util.potion.KelpPotionEffect;
import de.pxav.kelp.core.entity.util.potion.KelpPotionEffectType;
import de.pxav.kelp.core.inventory.type.SimpleEntityEquipment;
import de.pxav.kelp.core.world.KelpLocation;
import org.bukkit.entity.LivingEntity;

import java.util.Collection;
import java.util.Set;

public interface LivingKelpEntity<T extends LivingKelpEntity<?>> extends KelpEntity<T>, DamageableEntity<T> {

  T addPotionEffect(KelpPotionEffect potionEffect);

  Collection<KelpPotionEffect> getActivePotionEffects();

  default Collection<KelpPotionEffectType> getActivePotionEffectTypes() {
    Set<KelpPotionEffectType> output = Sets.newHashSet();
    for (KelpPotionEffect effect : getActivePotionEffects()) {
      output.add(effect.getEffectType());
    }
    return output;
  }

  T removePotionEffect(KelpPotionEffectType effectType);

  /**
   * Gets the location of the entity's eyes. When you get
   * the normal location of a player for example, the feet location
   * will be returned. This method gets the eye location depending
   * on the current entity type.
   *
   * @return The location of the entity's eyes.
   */
  KelpLocation getEyeLocation();

  SimpleEntityEquipment getEquipment();

}
