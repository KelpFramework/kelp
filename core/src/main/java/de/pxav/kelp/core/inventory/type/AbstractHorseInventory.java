package de.pxav.kelp.core.inventory.type;

import de.pxav.kelp.core.inventory.item.KelpItem;

public interface AbstractHorseInventory<T extends AbstractHorseInventory<T>> extends StorageInventory<T> {

  KelpItem getSaddle();

  T setSaddle(KelpItem saddleItem);

}
