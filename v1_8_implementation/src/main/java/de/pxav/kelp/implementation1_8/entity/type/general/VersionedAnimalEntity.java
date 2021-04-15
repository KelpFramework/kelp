package de.pxav.kelp.implementation1_8.entity.type.general;

import de.pxav.kelp.core.entity.KelpEntityType;
import de.pxav.kelp.core.entity.type.general.AnimalEntity;
import net.minecraft.server.v1_8_R3.Entity;
import org.bukkit.Location;

public class VersionedAnimalEntity<T extends AnimalEntity<T>>
  extends VersionedMobileEntity<T> implements AnimalEntity<T> {

  public VersionedAnimalEntity(Entity entityHandle, KelpEntityType entityType, Location initialLocation) {
    super(entityHandle, entityType, initialLocation);
  }

  @Override
  public boolean isLoveMode() {
    return false;
  }

  @Override
  public int getLoveModeTicks() {
    return 0;
  }

  @Override
  public T setLoveModeTicks() {
    return null;
  }

}
