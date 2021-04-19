package de.pxav.kelp.core.entity.type;

import de.pxav.kelp.core.entity.KelpEntity;
import de.pxav.kelp.core.entity.type.general.KelpProjectile;
import de.pxav.kelp.core.entity.util.FishHookState;

public interface FishHookEntity extends KelpProjectile<FishHookEntity> {

  int getMinWaitTime();

  void setMinWaitTime(int minWaitTime);

  int getMaxWaitTime();

  void setMaxWaitTime(int maxWaitTime);

  boolean getApplyLure();

  void setApplyLure(boolean var1);

  boolean isInOpenWater();

  KelpEntity<?> getHookedEntity();

  void setHookedEntity(KelpEntity<?> hookedEntity);

  boolean pullHookedEntity();

  FishHookState getFishHookState();

}
