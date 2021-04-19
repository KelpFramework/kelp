package de.pxav.kelp.core.entity.type;

import de.pxav.kelp.core.entity.KelpEntity;
import de.pxav.kelp.core.inventory.item.KelpItem;
import de.pxav.kelp.core.world.KelpLocation;

public interface EnderSignalEntity extends KelpEntity<EnderSignalEntity> {

  KelpLocation getTargetLocation();

  void setTargetLocation(KelpLocation var1);

  boolean getDropItem();

  void setDropItem(boolean dropItem);

  KelpItem getItem();

  void setItem(KelpItem item);

  int getDespawnTimer();

  void setDespawnTimer(int despawnTimer);

}
