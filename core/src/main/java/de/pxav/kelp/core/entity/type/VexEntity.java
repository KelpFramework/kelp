package de.pxav.kelp.core.entity.type;

import de.pxav.kelp.core.entity.type.general.MonsterEntity;

public interface VexEntity extends MonsterEntity<VexEntity> {

  boolean isCharging();

  VexEntity setCharging(boolean charging);

}
