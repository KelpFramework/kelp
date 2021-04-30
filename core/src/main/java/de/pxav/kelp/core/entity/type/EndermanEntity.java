package de.pxav.kelp.core.entity.type;

import de.pxav.kelp.core.entity.type.general.MonsterEntity;
import de.pxav.kelp.core.inventory.material.KelpMaterial;

public interface EndermanEntity extends MonsterEntity<EndermanEntity> {

  KelpMaterial getCarriedBlock();

  EndermanEntity setCarriedBlock(KelpMaterial carriedBlock);

}
