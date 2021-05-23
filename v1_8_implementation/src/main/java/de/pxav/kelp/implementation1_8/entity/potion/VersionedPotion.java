package de.pxav.kelp.implementation1_8.entity.potion;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import de.pxav.kelp.core.entity.LivingKelpEntity;
import de.pxav.kelp.core.entity.util.potion.KelpPotionEffect;
import de.pxav.kelp.core.entity.util.potion.PotionEffects;
import de.pxav.kelp.core.entity.util.potion.KelpPotionEffectType;
import de.pxav.kelp.core.entity.util.potion.PotionVersionTemplate;
import de.pxav.kelp.core.version.Versioned;
import org.bukkit.entity.LivingEntity;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

@Versioned
public class VersionedPotion extends PotionVersionTemplate {

  @Override
  public PotionEffectType getBukkitPotion(KelpPotionEffectType effectClass) {
    String effectName = getPotionMappings().get(effectClass);
    if (effectName.startsWith("IMPL_")) {
      return null;
    }
    return PotionEffectType.getByName(effectName);
  }

  @Override
  public void applyTo(LivingKelpEntity<?> entity, KelpPotionEffect potionEffect) {
    LivingEntity livingEntity = (LivingEntity) entity.getBukkitEntity();

    // if the potion is natively available in this server version
    if (potionEffect.getEffectType().isBukkitEffect()) {
      PotionEffectType effectType = getBukkitPotion(potionEffect.getEffectType());
      livingEntity.addPotionEffect(new PotionEffect(
        effectType,
        potionEffect.getDurationInTicks(),
        potionEffect.getLevel() - 1,
        false,
        potionEffect.particlesShown()));
      return;
    }

    // if the potion is emulated by Kelp
    potionEffect.getEffectType().onConsume(entity, potionEffect);

  }

  @Override
  public KelpPotionEffect fetchEffect(PotionEffect effect) {
    return KelpPotionEffect.create()
      .effectType(getKelpPotion(effect.getType()))
      .level(effect.getAmplifier() + 1)
      .showParticles(effect.hasParticles())
      .durationTicks(effect.getDuration());
  }

  @Override
  public KelpPotionEffectType getKelpPotion(PotionEffectType effectType) {
    return getPotionMappings().inverse().get(effectType.getName().toLowerCase());
  }

  private BiMap<KelpPotionEffectType, String> getPotionMappings() {
    BiMap<KelpPotionEffectType, String> output = HashBiMap.create();

    output.put(PotionEffects.ABSORPTION, PotionEffectType.ABSORPTION.getName().toLowerCase());
    output.put(PotionEffects.HASTE, PotionEffectType.FAST_DIGGING.getName().toLowerCase());

    output.put(PotionEffects.LEVITATION, "IMPL_LEVITATION");

    return output;
  }

}
