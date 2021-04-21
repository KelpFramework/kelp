package de.pxav.kelp.core.entity.type;

import de.pxav.kelp.core.entity.type.general.GolemEntity;
import de.pxav.kelp.core.inventory.metadata.Color;

public interface ShulkerEntity extends GolemEntity<ShulkerEntity> {

  Color getDyeColor();

  ShulkerEntity setDyeColor();

}
