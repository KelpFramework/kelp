package de.pxav.kelp.core.entity;

import de.pxav.kelp.core.entity.version.EntityVersionTemplate;
import de.pxav.kelp.core.entity.version.LivingEntityVersionTemplate;
import de.pxav.kelp.core.world.KelpLocation;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;

/**
 * A class description goes here.
 *
 * @author pxav
 */
public class LivingKelpEntity extends KelpEntity {

  protected LivingEntityVersionTemplate livingEntityVersionTemplate;
  protected LivingEntity bukkitLivingEntity;

  public LivingKelpEntity(Object minecraftEntity,
                          KelpEntityType entityType,
                          Location initialLocation,
                          int entityId,
                          EntityVersionTemplate entityVersionTemplate,
                          LivingEntityVersionTemplate livingEntityVersionTemplate,
                          LivingEntity bukkitLivingEntity) {
    super(minecraftEntity, entityType, initialLocation, entityId, entityVersionTemplate);
    this.livingEntityVersionTemplate = livingEntityVersionTemplate;
    this.bukkitLivingEntity = bukkitLivingEntity;
  }

  public LivingKelpEntity() {}

  public LivingKelpEntity bukkitLivingEntity(LivingEntity bukkitLivingEntity) {
    this.bukkitLivingEntity = bukkitLivingEntity;
    return this;
  }

  public LivingKelpEntity livingEntityVersionTemplate(LivingEntityVersionTemplate livingEntityVersionTemplate) {
    this.livingEntityVersionTemplate = livingEntityVersionTemplate;
    return this;
  }

  public LivingEntity toBukkitLivingEntity() {
    return bukkitLivingEntity;
  }

  public KelpLocation getEyeLocation() {
    return this.livingEntityVersionTemplate.getEyeLocation(bukkitLivingEntity);
  }

}
