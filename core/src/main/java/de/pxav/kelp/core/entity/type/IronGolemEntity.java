package de.pxav.kelp.core.entity.type;

import de.pxav.kelp.core.entity.type.general.GolemEntity;

public interface IronGolemEntity extends GolemEntity<IronGolemEntity> {

  boolean isPlayerCreated();

  IronGolemEntity setPlayerCreated(boolean playerCreated);

}
