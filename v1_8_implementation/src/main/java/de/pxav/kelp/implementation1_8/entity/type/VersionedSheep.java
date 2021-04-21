package de.pxav.kelp.implementation1_8.entity.type;

import de.pxav.kelp.core.entity.KelpEntityType;
import de.pxav.kelp.core.entity.type.SheepEntity;
import de.pxav.kelp.core.entity.version.EntityTypeVersionTemplate;
import de.pxav.kelp.core.reflect.ReflectionUtil;
import de.pxav.kelp.implementation1_8.entity.type.general.VersionedBreedableAnimal;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityAnimal;
import net.minecraft.server.v1_8_R3.EntitySheep;
import org.bukkit.Location;
import org.bukkit.entity.Animals;

public class VersionedSheep
  extends VersionedBreedableAnimal<SheepEntity>
  implements SheepEntity {

  private EntitySheep sheepHandle;

  public VersionedSheep(Entity entityHandle,
                        KelpEntityType entityType,
                        Location initialLocation,
                        EntityTypeVersionTemplate entityTypeVersionTemplate,
                        ReflectionUtil reflectionUtil) {
    super(entityHandle, entityType, initialLocation, entityTypeVersionTemplate, reflectionUtil);
    this.sheepHandle = (EntitySheep) entityHandle;
  }

  @Override
  public boolean isSheared() {
    return sheepHandle.isSheared();
  }

  @Override
  public SheepEntity setSheared(boolean sheared) {
    sheepHandle.setSheared(sheared);
    return this;
  }

}
