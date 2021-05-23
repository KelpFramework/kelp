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
    output.put(PotionEffects.BLINDNESS, PotionEffectType.BLINDNESS.getName().toLowerCase());
    output.put(PotionEffects.FIRE_RESISTANCE, PotionEffectType.FIRE_RESISTANCE.getName().toLowerCase());
    output.put(PotionEffects.HASTE, PotionEffectType.FAST_DIGGING.getName().toLowerCase());
    output.put(PotionEffects.HEALTH_BOOST, PotionEffectType.HEALTH_BOOST.getName().toLowerCase());
    output.put(PotionEffects.HUNGER, PotionEffectType.HUNGER.getName().toLowerCase());
    output.put(PotionEffects.INSTANT_DAMAGE, PotionEffectType.HARM.getName().toLowerCase());
    output.put(PotionEffects.INSTANT_HEAL, PotionEffectType.HEAL.getName().toLowerCase());
    output.put(PotionEffects.INVISIBILITY, PotionEffectType.INVISIBILITY.getName().toLowerCase());
    output.put(PotionEffects.JUMP_BOOST, PotionEffectType.JUMP.getName().toLowerCase());
    output.put(PotionEffects.MINING_FATIGUE, PotionEffectType.SLOW_DIGGING.getName().toLowerCase());
    output.put(PotionEffects.NAUSEA, PotionEffectType.CONFUSION.getName().toLowerCase());
    output.put(PotionEffects.NIGHT_VISION, PotionEffectType.NIGHT_VISION.getName().toLowerCase());
    output.put(PotionEffects.POISON, PotionEffectType.POISON.getName().toLowerCase());
    output.put(PotionEffects.REGENERATION, PotionEffectType.REGENERATION.getName().toLowerCase());
    output.put(PotionEffects.RESISTANCE, PotionEffectType.DAMAGE_RESISTANCE.getName().toLowerCase());
    output.put(PotionEffects.SATURATION, PotionEffectType.SATURATION.getName().toLowerCase());
    output.put(PotionEffects.SLOWNESS, PotionEffectType.SLOW.getName().toLowerCase());
    output.put(PotionEffects.SPEED, PotionEffectType.SPEED.getName().toLowerCase());
    output.put(PotionEffects.STRENGTH, PotionEffectType.INCREASE_DAMAGE.getName().toLowerCase());
    output.put(PotionEffects.WATER_BREATHING, PotionEffectType.WATER_BREATHING.getName().toLowerCase());
    output.put(PotionEffects.WEAKNESS, PotionEffectType.WEAKNESS.getName().toLowerCase());
    output.put(PotionEffects.WITHER, PotionEffectType.WITHER.getName().toLowerCase());

    output.put(PotionEffects.BAD_LUCK, "IMPL_BAD_LUCK");
    output.put(PotionEffects.BAD_OMEN, "IMPL_BAD_OMEN");
    output.put(PotionEffects.CONDUIT_POWER, "IMPL_CONDUIT_POWER");
    output.put(PotionEffects.HERO_OF_THE_VILLAGE, "IMPL_HERO_OF_THE_VILLAGE");
    output.put(PotionEffects.DOLPHINS_GRACE, "IMPL_DOLPHINS_GRACE");
    output.put(PotionEffects.GLOWING, "IMPL_GLOWING");
    output.put(PotionEffects.LUCK, "IMPL_LUCK");
    output.put(PotionEffects.SLOW_FALLING, "IMPL_SLOW_FALLING");
    output.put(PotionEffects.LEVITATION, "IMPL_LEVITATION");

    return output;
  }

}
