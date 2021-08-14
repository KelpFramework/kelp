package de.pxav.kelp.implementation1_8.entity.type.general;

import de.pxav.kelp.core.entity.KelpEntityType;
import de.pxav.kelp.core.entity.type.general.VehicleEntity;
import de.pxav.kelp.core.entity.version.EntityTypeVersionTemplate;
import de.pxav.kelp.core.world.util.Vector3;
import de.pxav.kelp.implementation1_8.entity.VersionedEntity;
import net.minecraft.server.v1_8_R3.Entity;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class VersionedVehicle<T extends VehicleEntity<T>> extends VersionedEntity<T> implements VehicleEntity<T> {

  public VersionedVehicle(Entity entityHandle, KelpEntityType entityType, Location initialLocation, EntityTypeVersionTemplate entityTypeVersionTemplate) {
    super(entityHandle, entityType, initialLocation, entityTypeVersionTemplate);
  }

  @Override
  public Vector3 getVehicleVelocity() {
    return getVelocity();
  }

  @Override
  public T setVehicleVelocity(Vector3 vehicleVelocity) {
    return setVelocity(vehicleVelocity);
  }

}
