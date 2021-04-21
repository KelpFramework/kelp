package de.pxav.kelp.implementation1_8.inventory;

import de.pxav.kelp.core.inventory.item.KelpItem;
import de.pxav.kelp.core.inventory.type.HorseInventory;
import org.bukkit.inventory.Inventory;

public class VersionedHorseInventory
  extends VersionedAbstractHorseInventory<HorseInventory>
  implements HorseInventory {

  public VersionedHorseInventory(Inventory inventory) {
    super(inventory);
  }

  @Override
  public KelpItem getArmor() {
    return null;
  }

  @Override
  public HorseInventory setArmor(KelpItem armorItem) {
    return null;
  }

}
