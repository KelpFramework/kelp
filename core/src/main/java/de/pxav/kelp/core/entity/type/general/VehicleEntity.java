package de.pxav.kelp.core.entity.type.general;

import de.pxav.kelp.core.entity.KelpEntity;
import de.pxav.kelp.core.world.util.Vector3;
import org.bukkit.util.Vector;

public interface VehicleEntity<T extends VehicleEntity<?>> extends KelpEntity<T> {

  Vector3 getVehicleVelocity();

  T setVehicleVelocity(Vector3 vehicleVelocity);

}
