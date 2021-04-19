package de.pxav.kelp.core.entity.type;

import de.pxav.kelp.core.entity.type.general.AnimalEntity;
import de.pxav.kelp.core.entity.util.CatType;
import de.pxav.kelp.core.inventory.metadata.Color;

public interface CatEntity extends AnimalEntity<CatEntity> {

  Color getCollarDyeColor();

  CatEntity setCollarDyeColor(Color color);

  CatType getCatType();

  CatEntity setCatType(CatType catType);

}
