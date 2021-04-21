package de.pxav.kelp.core.entity.type;

import de.pxav.kelp.core.entity.type.general.MobileEntity;

public interface SlimeEntity extends MobileEntity<SlimeEntity> {

  int getSlimeSize();

  SlimeEntity setSlimeSize();

}
