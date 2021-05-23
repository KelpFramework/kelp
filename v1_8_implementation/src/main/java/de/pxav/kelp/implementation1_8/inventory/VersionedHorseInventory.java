package de.pxav.kelp.implementation1_8.inventory;

import de.pxav.kelp.core.inventory.item.KelpItem;
import de.pxav.kelp.core.inventory.type.HorseInventory;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftInventoryHorse;
import org.bukkit.inventory.Inventory;

public class VersionedHorseInventory
  extends VersionedAbstractHorseInventory<HorseInventory>
  implements HorseInventory {

  public VersionedHorseInventory(Inventory inventory) {
    super(inventory);
  }

  @Override
  public KelpItem getArmor() {
    return getItemAt(1);
  }

  @Override
  public HorseInventory setArmor(KelpItem armorItem) {
    setItem(armorItem.slot(1));
    return this;
  }

}
