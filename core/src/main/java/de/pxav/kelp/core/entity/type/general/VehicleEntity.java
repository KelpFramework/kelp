package de.pxav.kelp.core.entity.type.general;

import de.pxav.kelp.core.entity.KelpEntity;
import org.bukkit.util.Vector;

public interface VehicleEntity<T extends VehicleEntity<?>> extends KelpEntity<T> {

  Vector getVehicleVelocity();

  T setVehicleVelocity(Vector vehicleVelocity);

}
