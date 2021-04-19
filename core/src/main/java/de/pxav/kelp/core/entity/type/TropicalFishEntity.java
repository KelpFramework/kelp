package de.pxav.kelp.core.entity.type;

import de.pxav.kelp.core.entity.type.general.AbstractFishEntity;
import de.pxav.kelp.core.inventory.metadata.Color;

public interface TropicalFishEntity extends AbstractFishEntity<TropicalFishEntity> {

  // TODO: Add patterns!

  Color getBodyDyeColor();

  Color getPatternDyeColor();

  TropicalFishEntity setPatternDyeColor();

  TropicalFishEntity setBodyDyeColor();

}
