package de.pxav.kelp.core.entity.type;

import de.pxav.kelp.core.entity.KelpEntity;
import de.pxav.kelp.core.inventory.item.KelpItem;
import de.pxav.kelp.core.world.KelpLocation;

public interface EnderSignalEntity extends KelpEntity<EnderSignalEntity> {

  KelpLocation getTargetLocation();

  EnderSignalEntity setTargetLocation(KelpLocation var1);

  boolean getDropItem();

  EnderSignalEntity setDropItem(boolean dropItem);

  KelpItem getItem();

  EnderSignalEntity setItem(KelpItem item);

  int getDespawnTimer();

  EnderSignalEntity setDespawnTimer(int despawnTimer);

}
