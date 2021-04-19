package de.pxav.kelp.core.entity.type;

import de.pxav.kelp.core.entity.type.general.AbstractVillager;

public interface WanderingTraderEntity extends AbstractVillager<WanderingTraderEntity> {

  int getDespawnDelay();

  WanderingTraderEntity setDespawnDelay(int despawnDelay);

}
