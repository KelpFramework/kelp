package de.pxav.kelp.core.entity.type;

import de.pxav.kelp.core.entity.type.general.MobileEntity;

public interface BatEntity extends MobileEntity<BatEntity> {

  boolean isAwake();

  BatEntity setAwake(boolean awake);

}
