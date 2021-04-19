package de.pxav.kelp.core.entity.type;

import de.pxav.kelp.core.entity.type.general.VehicleEntity;
import de.pxav.kelp.core.inventory.material.KelpMaterial;

public interface BoatEntity extends VehicleEntity<BoatEntity> {

  KelpMaterial getBoatType();

  BoatEntity setBoatType(KelpMaterial boatType);

}
