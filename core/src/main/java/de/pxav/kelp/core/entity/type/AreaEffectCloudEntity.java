package de.pxav.kelp.core.entity.type;

import de.pxav.kelp.core.entity.KelpEntity;
import de.pxav.kelp.core.entity.KelpEntityType;
import de.pxav.kelp.core.entity.type.general.ProjectileLauncher;
import de.pxav.kelp.core.entity.util.potion.KelpPotionEffectType;
import de.pxav.kelp.core.inventory.metadata.Color;
import de.pxav.kelp.core.particle.type.ParticleType;

import java.util.List;

public interface AreaEffectCloudEntity extends KelpEntity<AreaEffectCloudEntity> {

  @Override
  default KelpEntityType getType() {
    return KelpEntityType.AREA_EFFECT_CLOUD;
  }

  int getDuration();

  void setDuration(int var1);

  int getWaitTime();

  void setWaitTime(int var1);

  int getReapplicationDelay();

  void setReapplicationDelay(int var1);

  int getDurationOnUse();

  void setDurationOnUse(int var1);

  float getRadius();

  void setRadius(float var1);

  float getRadiusOnUse();

  void setRadiusOnUse(float var1);

  float getRadiusPerTick();

  void setRadiusPerTick(float var1);

  ParticleType getParticle();

  void setParticle(ParticleType particleType);

  void setBasePotionEffect(KelpPotionEffectType potionEffect);

  KelpPotionEffectType getBasePotionEffect();

  boolean hasCustomEffects();

  List<KelpPotionEffectType> getCustomEffects();

  boolean addCustomEffect(KelpPotionEffectType effect, boolean var2);

  boolean removeCustomEffect(KelpPotionEffectType effect);

  boolean hasCustomEffect(KelpPotionEffectType effect);

  void clearCustomEffects();

  Color getColor();

  void setColor(Color color);

  ProjectileLauncher<?> getSource();

  void setSource(ProjectileLauncher<?> source);

}
