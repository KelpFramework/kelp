package de.pxav.kelp.core.inventory.type;

import de.pxav.kelp.core.inventory.item.KelpItem;

public interface LlamaInventory extends AbstractHorseInventory<LlamaInventory> {

  KelpItem getDecoration();

  LlamaInventory setDecoration(KelpItem decoration);

}
