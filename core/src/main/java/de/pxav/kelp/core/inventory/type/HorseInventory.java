package de.pxav.kelp.core.inventory.type;

import de.pxav.kelp.core.inventory.item.KelpItem;

public interface HorseInventory extends AbstractHorseInventory<HorseInventory> {

  KelpItem getArmor();

  HorseInventory setArmor(KelpItem armorItem);

}
