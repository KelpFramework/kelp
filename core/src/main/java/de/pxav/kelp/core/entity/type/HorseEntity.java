package de.pxav.kelp.core.entity.type;

import de.pxav.kelp.core.entity.type.general.AbstractHorseEntity;
import de.pxav.kelp.core.entity.util.HorseColor;
import de.pxav.kelp.core.entity.util.HorseStyle;
import de.pxav.kelp.core.inventory.type.HorseInventory;

public interface HorseEntity extends AbstractHorseEntity<HorseEntity> {

  HorseInventory getInventory();

  HorseColor getHorseColor();

  HorseStyle getHorseStyle();

  HorseEntity setHorseColor(HorseColor color);

  HorseEntity setHorseStyle(HorseStyle style);

}
