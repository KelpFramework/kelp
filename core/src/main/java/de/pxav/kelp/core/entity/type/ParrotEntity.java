package de.pxav.kelp.core.entity.type;

import de.pxav.kelp.core.entity.type.general.AnimalEntity;
import de.pxav.kelp.core.entity.type.general.TameableAnimal;
import de.pxav.kelp.core.entity.util.ParrotType;

public interface ParrotEntity extends AnimalEntity<ParrotEntity>, TameableAnimal<ParrotEntity> {

  ParrotEntity setParrotType(ParrotType parrotType);

  ParrotType getParrotType();

}
