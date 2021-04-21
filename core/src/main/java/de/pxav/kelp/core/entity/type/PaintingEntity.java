package de.pxav.kelp.core.entity.type;

import de.pxav.kelp.core.entity.type.general.HangingEntity;
import de.pxav.kelp.core.entity.util.PaintingType;

public interface PaintingEntity extends HangingEntity<PaintingEntity> {

  PaintingEntity setPaintingType(PaintingType type);

  PaintingType getPaintingType();

  default int[] getPaintingDimensions() {
    return new int[] {getPaintingType().getWidth(), getPaintingType().getHeight()};
  }

}
