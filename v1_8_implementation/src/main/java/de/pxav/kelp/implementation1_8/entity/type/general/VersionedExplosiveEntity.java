package de.pxav.kelp.implementation1_8.entity.type.general;

import de.pxav.kelp.core.entity.KelpEntityType;
import de.pxav.kelp.core.entity.type.general.ExplosiveEntity;
import de.pxav.kelp.core.world.util.ExplosionPower;
import de.pxav.kelp.implementation1_8.entity.VersionedEntity;
import net.minecraft.server.v1_8_R3.Entity;
import org.bukkit.Location;

public class VersionedExplosiveEntity<T extends ExplosiveEntity<T>>
  extends VersionedEntity<T>
  implements ExplosiveEntity<T> {

  public VersionedExplosiveEntity(Entity entityHandle, KelpEntityType entityType, Location initialLocation) {
    super(entityHandle, entityType, initialLocation);
  }

  @Override
  public ExplosionPower getExplosionPower() {
    return null;
  }

  @Override
  public T setExplosionPower(ExplosionPower power) {
    return null;
  }

  @Override
  public boolean createsFire() {
    return false;
  }

  @Override
  public T canCreateFire(boolean create) {
    return null;
  }
}
