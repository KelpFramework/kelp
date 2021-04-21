package de.pxav.kelp.implementation1_8.inventory;

import de.pxav.kelp.core.inventory.item.KelpItem;
import de.pxav.kelp.core.inventory.type.AbstractHorseInventory;
import org.bukkit.inventory.Inventory;

public class VersionedAbstractHorseInventory<T extends AbstractHorseInventory<T>> extends VersionedStorageInventory<T> implements AbstractHorseInventory<T> {

  public VersionedAbstractHorseInventory(Inventory inventory) {
    super(inventory);
  }

  @Override
  public KelpItem getSaddle() {
    return getItemAt(0);
  }

  @Override
  public T setSaddle(KelpItem saddleItem) {
    setItem(saddleItem.slot(1));
    return (T) this;
  }

}
