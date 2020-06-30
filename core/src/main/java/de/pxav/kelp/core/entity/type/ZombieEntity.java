package de.pxav.kelp.core.entity.type;

import de.pxav.kelp.core.entity.KelpEntityType;
import de.pxav.kelp.core.entity.LivingKelpEntity;
import de.pxav.kelp.core.entity.version.EntityVersionTemplate;
import de.pxav.kelp.core.entity.version.LivingEntityVersionTemplate;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;

/**
 * A class description goes here.
 *
 * @author pxav
 */
public class ZombieEntity extends LivingKelpEntity {

  private boolean isBaby = false;

  public ZombieEntity() {}

  public ZombieEntity(EntityVersionTemplate entityVersionTemplate, LivingEntityVersionTemplate livingEntityVersionTemplate, LivingEntity livingEntity, Object entity, int entityId, Location location, boolean isBaby) {
    super(entity, KelpEntityType.ZOMBIE, location, entityId, entityVersionTemplate, livingEntityVersionTemplate, livingEntity);
    this.isBaby = isBaby;
  }

  public boolean isBaby() {
    return isBaby;
  }

  public void setBaby(boolean baby) {
    isBaby = baby;
  }

}
