package de.pxav.kelp.implementation1_8.entity.type.general;

import de.pxav.kelp.core.entity.KelpEntityType;
import de.pxav.kelp.core.entity.type.general.BreedableAnimalEntity;
import de.pxav.kelp.core.entity.version.EntityTypeVersionTemplate;
import de.pxav.kelp.core.reflect.ReflectionUtil;
import net.minecraft.server.v1_8_R3.Entity;
import org.bukkit.Location;

public class VersionedBreedableAnimal<T extends BreedableAnimalEntity<T>>
  extends VersionedAnimalEntity<T>
  implements BreedableAnimalEntity<T> {

  public VersionedBreedableAnimal(Entity entityHandle, KelpEntityType entityType, Location initialLocation, EntityTypeVersionTemplate entityTypeVersionTemplate) {
    super(entityHandle, entityType, initialLocation, entityTypeVersionTemplate);
  }

  //TODO cannot be implemented. Lack of server-side methods.

  @Override
  public boolean canBreed() {
    return false;
  }

  @Override
  public T setBreedable(boolean breedable) {
    return null;
  }

}
