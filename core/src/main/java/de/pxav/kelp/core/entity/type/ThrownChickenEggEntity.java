package de.pxav.kelp.core.entity.type;

import de.pxav.kelp.core.common.MathUtils;
import de.pxav.kelp.core.entity.type.general.KelpProjectile;

public interface ThrownChickenEggEntity extends KelpProjectile<ThrownChickenEggEntity> {

  default boolean spawnsChicken() {
    return MathUtils.perCentChance(0.125);
  }

  default int getSpawnedChickens() {
    if (spawnsChicken()) {
      if (MathUtils.perCentChance(0.03125)) {
        return 4;
      }
      return 1;
    }
    return 0;
  }

}
