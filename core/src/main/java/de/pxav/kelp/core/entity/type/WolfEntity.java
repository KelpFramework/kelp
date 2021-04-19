package de.pxav.kelp.core.entity.type;

import de.pxav.kelp.core.entity.type.general.AnimalEntity;
import de.pxav.kelp.core.entity.type.general.TameableAnimal;
import de.pxav.kelp.core.inventory.metadata.Color;

public interface WolfEntity extends AnimalEntity<WolfEntity>, TameableAnimal<WolfEntity> {

  boolean isAngry();

  WolfEntity setAngry(boolean angry);

  Color getCollarDyeColor();

  WolfEntity setCollarDyeColor();

}
