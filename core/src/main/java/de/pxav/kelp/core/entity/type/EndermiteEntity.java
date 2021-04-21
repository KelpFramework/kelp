package de.pxav.kelp.core.entity.type;

import de.pxav.kelp.core.entity.type.general.MonsterEntity;

public interface EndermiteEntity extends MonsterEntity<EndermiteEntity> {

  boolean isPlayerSpawned();

  EndermiteEntity setPlayerSpawned(boolean playerSpawned);

}
