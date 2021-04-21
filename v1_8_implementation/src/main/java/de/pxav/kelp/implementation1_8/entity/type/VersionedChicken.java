package de.pxav.kelp.implementation1_8.entity.type;

import de.pxav.kelp.core.entity.KelpEntityType;
import de.pxav.kelp.core.entity.type.ChickenEntity;
import de.pxav.kelp.core.entity.version.EntityTypeVersionTemplate;
import de.pxav.kelp.core.reflect.ReflectionUtil;
import de.pxav.kelp.implementation1_8.entity.type.general.VersionedBreedableAnimal;
import net.minecraft.server.v1_8_R3.Entity;
import org.bukkit.Location;

public class VersionedChicken extends VersionedBreedableAnimal<ChickenEntity> implements ChickenEntity  {

  public VersionedChicken(Entity entityHandle, KelpEntityType entityType, Location initialLocation, EntityTypeVersionTemplate entityTypeVersionTemplate, ReflectionUtil reflectionUtil) {
    super(entityHandle, entityType, initialLocation, entityTypeVersionTemplate, reflectionUtil);
  }

}
