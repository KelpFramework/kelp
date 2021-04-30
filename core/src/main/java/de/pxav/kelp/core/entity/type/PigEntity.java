package de.pxav.kelp.core.entity.type;

import de.pxav.kelp.core.entity.type.general.AnimalEntity;
import de.pxav.kelp.core.entity.type.general.BreedableAnimalEntity;
import de.pxav.kelp.core.entity.type.general.VehicleEntity;
import org.bukkit.entity.Pig;

public interface PigEntity extends AnimalEntity<PigEntity>, BreedableAnimalEntity<PigEntity>, VehicleEntity<PigEntity> {

  boolean hasSaddle();

  PigEntity setSaddled(boolean saddled);

}
