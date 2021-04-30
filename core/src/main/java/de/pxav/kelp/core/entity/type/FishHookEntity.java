package de.pxav.kelp.core.entity.type;

import de.pxav.kelp.core.entity.KelpEntity;
import de.pxav.kelp.core.entity.type.general.KelpProjectile;
import de.pxav.kelp.core.entity.util.FishHookState;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftFishHook;

public interface FishHookEntity extends KelpProjectile<FishHookEntity> {

  KelpEntity<?> getOwner();

  int getWaitTimeInTicks();

  FishHookEntity setWaitTime(int waitTicks);

  int getMinWaitTime();

  FishHookEntity setMinWaitTime(int minWaitTime);

  int getMaxWaitTime();

  FishHookEntity setMaxWaitTime(int maxWaitTime);

  boolean hasLure();

  FishHookEntity setApplyLure(boolean hasLure);

  boolean isInOpenWater();

  KelpEntity<?> getHookedEntity();

  FishHookEntity setHookedEntity(KelpEntity<?> hookedEntity);

  FishHookEntity pullHookedEntity();

  FishHookState getFishHookState();

}
