package de.pxav.kelp.core.entity.type;

import de.pxav.kelp.core.entity.KelpEntity;
import de.pxav.kelp.core.inventory.material.KelpMaterial;

public interface FallingBlockEntity extends KelpEntity<FallingBlockEntity> {

  boolean canHurtEntities();

  boolean willDropItem();

  FallingBlockEntity dropsItem(boolean dropsItem);

  FallingBlockEntity hurtEntities(boolean hurtEntities);

  KelpMaterial getMaterial();

}
