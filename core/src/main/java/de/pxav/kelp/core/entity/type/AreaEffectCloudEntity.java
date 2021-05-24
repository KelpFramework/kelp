package de.pxav.kelp.core.entity.type;

import de.pxav.kelp.core.KelpPlugin;
import de.pxav.kelp.core.entity.KelpEntity;
import de.pxav.kelp.core.entity.KelpEntityType;
import de.pxav.kelp.core.entity.type.general.ProjectileLauncher;
import de.pxav.kelp.core.entity.util.potion.KelpPotionEffectType;
import de.pxav.kelp.core.entity.version.EntityTypeVersionTemplate;
import de.pxav.kelp.core.inventory.metadata.Color;
import de.pxav.kelp.core.particle.type.ParticleType;
import de.pxav.kelp.core.world.KelpLocation;
import org.bukkit.entity.Entity;

import java.util.List;

public interface AreaEffectCloudEntity extends KelpEntity<AreaEffectCloudEntity> {

  /**
   * Creates a new entity of this type at the given location.
   *
   * While this creates a new instance, it won't actually spawn the entity.
   * You can first do some modifications on it and then call the
   * {@link KelpEntity#spawn()} method.
   *
   * If you don't want to create a new entity, but just a new kelp
   * entity instance based of an existing bukkit entity, you can use
   * {@link #from(Entity)} instead.
   *
   * @param location The location, where the entity should be spawned later.
   * @return A new instance of a sheep entity.
   */
  static AreaEffectCloudEntity create(KelpLocation location) {
    return (AreaEffectCloudEntity) KelpPlugin.getInjector().getInstance(EntityTypeVersionTemplate.class)
      .newKelpEntity(getEntityType(), location.getBukkitLocation());
  }

  /**
   * Takes a bukkit entity and converts it to a kelp entity of the same type.
   *
   * This can be used if you are for example handling an event that returns a bukkit entity,
   * but you want to use a kelp entity for your operations. You can also use
   * the more general method provided by {@link de.pxav.kelp.core.entity.KelpEntity the
   * kelp entity base class}: {@link de.pxav.kelp.core.entity.KelpEntity#from(Entity)},
   * but this way you don't have to cast your entity to the specific type
   * manually.
   *
   * @param entity The entity you want to convert.
   * @return
   */
  static AreaEffectCloudEntity from(Entity entity) {
    return (AreaEffectCloudEntity) KelpPlugin.getInjector().getInstance(EntityTypeVersionTemplate.class)
      .getKelpEntity(entity);
  }

  static KelpEntityType getEntityType() {
    return KelpEntityType.AREA_EFFECT_CLOUD;
  }

  @Override
  default KelpEntityType getType() {
    return getEntityType();
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
