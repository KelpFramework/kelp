package de.pxav.kelp.core.entity.type;

import de.pxav.kelp.core.entity.type.general.AnimalEntity;
import de.pxav.kelp.core.entity.type.general.BreedableAnimalEntity;

public interface StriderEntity extends AnimalEntity<StriderEntity>, BreedableAnimalEntity<StriderEntity> {

  boolean isShivering();

  StriderEntity setShivering(boolean shivering);

}
