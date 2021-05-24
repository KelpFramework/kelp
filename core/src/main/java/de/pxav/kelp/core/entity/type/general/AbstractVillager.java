package de.pxav.kelp.core.entity.type.general;

import de.pxav.kelp.core.inventory.type.StorageInventory;

public interface AbstractVillager<T extends AbstractVillager<?>> extends HumanEntity<T> {

  StorageInventory<?> getStorageInventory();

}
