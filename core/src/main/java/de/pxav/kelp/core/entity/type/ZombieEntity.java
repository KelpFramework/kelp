package de.pxav.kelp.core.entity.type;

import de.pxav.kelp.core.entity.KelpEntity;
import de.pxav.kelp.core.entity.KelpEntityType;
import org.bukkit.Location;

/**
 * A class description goes here.
 *
 * @author pxav
 */
public class ZombieEntity extends KelpEntity {

  private boolean isBaby = false;

  public ZombieEntity() {}

  public ZombieEntity(Object entity, int entityId, Location location, boolean isBaby) {
    super(entity, KelpEntityType.ZOMBIE, location, entityId);
    this.isBaby = isBaby;
  }

  public boolean isBaby() {
    return isBaby;
  }

  public void setBaby(boolean baby) {
    isBaby = baby;
  }

}
