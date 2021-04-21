package de.pxav.kelp.core.entity.type;

import de.pxav.kelp.core.entity.type.general.MinecartEntity;
import de.pxav.kelp.core.inventory.type.StorageInventory;

public interface StorageMinecart extends MinecartEntity<StorageMinecart> {

  StorageInventory<?> getInventory();

}
