package de.pxav.kelp.core.entity.type;

import de.pxav.kelp.core.entity.type.general.AbstractHorseEntity;
import de.pxav.kelp.core.entity.util.LlamaColor;
import de.pxav.kelp.core.inventory.type.LlamaInventory;

public interface LlamaEntity extends AbstractHorseEntity<LlamaEntity> {

  LlamaInventory getInventory();

  int getLlamaStrength();

  LlamaEntity setLlamaStrength(int llamaStrength);

  LlamaColor getBodyColor();

  LlamaEntity setBodyColor(LlamaColor llamaColor);

}
