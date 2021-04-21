package de.pxav.kelp.core.entity.type;

import de.pxav.kelp.core.entity.type.general.AnimalEntity;
import de.pxav.kelp.core.entity.type.general.BreedableAnimalEntity;
import de.pxav.kelp.core.entity.util.RabbitType;

public interface RabbitEntity extends AnimalEntity<RabbitEntity>, BreedableAnimalEntity<RabbitEntity> {

  RabbitType getRabbitType();

  RabbitEntity setRabbitType(RabbitType rabbitType);

}
