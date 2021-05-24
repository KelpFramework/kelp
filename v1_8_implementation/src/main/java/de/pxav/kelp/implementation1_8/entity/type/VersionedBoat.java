package de.pxav.kelp.implementation1_8.entity.type;

import de.pxav.kelp.core.entity.KelpEntityType;
import de.pxav.kelp.core.entity.type.BoatEntity;
import de.pxav.kelp.core.entity.version.EntityTypeVersionTemplate;
import de.pxav.kelp.core.inventory.material.KelpMaterial;
import de.pxav.kelp.implementation1_8.entity.type.general.VersionedVehicle;
import net.minecraft.server.v1_8_R3.Entity;
import org.bukkit.Location;

public class VersionedBoat extends VersionedVehicle<BoatEntity> implements BoatEntity {

  public VersionedBoat(Entity entityHandle, KelpEntityType entityType, Location initialLocation, EntityTypeVersionTemplate entityTypeVersionTemplate) {
    super(entityHandle, entityType, initialLocation, entityTypeVersionTemplate);
  }

  // no types other than oak allowed
  @Override
  public KelpMaterial getBoatType() {
    return KelpMaterial.OAK_BOAT;
  }

  @Override
  public BoatEntity setBoatType(KelpMaterial boatType) {
    return this;
  }

}
