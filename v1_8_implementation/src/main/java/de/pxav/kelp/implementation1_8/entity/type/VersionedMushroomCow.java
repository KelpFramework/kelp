package de.pxav.kelp.implementation1_8.entity.type;

import de.pxav.kelp.core.entity.KelpEntityType;
import de.pxav.kelp.core.entity.type.MushroomCowEntity;
import de.pxav.kelp.core.entity.util.MushroomCowType;
import de.pxav.kelp.core.entity.version.EntityTypeVersionTemplate;
import net.minecraft.server.v1_8_R3.Entity;
import org.bukkit.Location;

public class VersionedMushroomCow extends VersionedCow implements MushroomCowEntity {

  public VersionedMushroomCow(Entity entityHandle, KelpEntityType entityType, Location initialLocation, EntityTypeVersionTemplate entityTypeVersionTemplate) {
    super(entityHandle, entityType, initialLocation, entityTypeVersionTemplate);
  }

  // other mushroom types than red are not available in 1.8
  @Override
  public MushroomCowType getMushroomType() {
    return MushroomCowType.RED_MUSHROOMS;
  }

  @Override
  public MushroomCowEntity setMushroomType(MushroomCowType mushroomType) {
    return this;
  }

}
