package de.pxav.kelp.implementation1_8.entity.type;

import de.pxav.kelp.core.entity.KelpEntityType;
import de.pxav.kelp.core.entity.type.ZombieEntity;
import de.pxav.kelp.core.entity.type.general.AgeableEntity;
import de.pxav.kelp.core.entity.version.EntityTypeVersionTemplate;
import de.pxav.kelp.implementation1_8.entity.type.general.VersionedAgeable;
import de.pxav.kelp.implementation1_8.entity.type.general.VersionedMonster;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityZombie;
import org.bukkit.Location;

// zombie not ageable in 1.8, so it implements the ageable interface directly. In newer versions inherit from VersionedAgeable.
public class VersionedZombie extends VersionedMonster<ZombieEntity> implements ZombieEntity, AgeableEntity<ZombieEntity> {

  private EntityZombie zombieHandle;

  public VersionedZombie(Entity entityHandle, KelpEntityType entityType, Location initialLocation, EntityTypeVersionTemplate entityTypeVersionTemplate) {
    super(entityHandle, entityType, initialLocation, entityTypeVersionTemplate);
    this.zombieHandle = (EntityZombie) entityHandle;
  }

  @Override
  public boolean isAgeLocked() {
    return false;
  }

  @Override
  public ZombieEntity setAgeLocked(boolean ageLocked) {
    return this;
  }

  @Override
  public ZombieEntity setBaby(boolean baby) {
    zombieHandle.setBaby(baby);
    return this;
  }

  @Override
  public boolean isBaby() {
    return zombieHandle.isBaby();
  }

  @Override
  public ZombieEntity setAge(int age) {
    return this;
  }

}
