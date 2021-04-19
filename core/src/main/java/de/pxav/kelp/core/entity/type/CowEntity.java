package de.pxav.kelp.core.entity.type;

import de.pxav.kelp.core.entity.type.general.AnimalEntity;
import de.pxav.kelp.core.entity.type.general.BreedableAnimalEntity;

public interface CowEntity extends AnimalEntity<CowEntity>, BreedableAnimalEntity<CowEntity> {
}
