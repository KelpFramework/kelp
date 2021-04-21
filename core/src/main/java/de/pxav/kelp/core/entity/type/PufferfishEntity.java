package de.pxav.kelp.core.entity.type;

import de.pxav.kelp.core.entity.type.general.WaterEntity;

public interface PufferfishEntity extends WaterEntity<PufferfishEntity> {

  int getPuffState();

  PufferfishEntity setPuffState();

}
