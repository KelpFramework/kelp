package de.pxav.kelp.core.entity.type;

import de.pxav.kelp.core.entity.type.general.ExplosiveEntity;
import de.pxav.kelp.core.entity.type.general.MonsterEntity;
import org.bukkit.Difficulty;

public interface CreeperEntity extends MonsterEntity<CreeperEntity>, ExplosiveEntity<CreeperEntity> {

  boolean isCharged();

  CreeperEntity setCharged(boolean charged);

  CreeperEntity setMaxFuseTicks(int maxFuseTicks);

  int getMaxFuseTicks();

  CreeperEntity setFuseTicks(int fuseTicks);

  int getFuseTicks();

  CreeperEntity explode();

  CreeperEntity ignite();

  boolean isIgnited();

  @Override
  default int getMaximumSpawnLightLevel() {
    return 7;
  }

  @Override
  default double getAttackDamage(Difficulty difficulty) {
    if (difficulty == Difficulty.EASY) {
      if (isCharged()) {
        return 43.5;
      }
      return 22.5;
    } else if (difficulty == Difficulty.NORMAL) {
      if (isCharged()) {
        return 85;
      }
      return 43;
    } if (difficulty == Difficulty.HARD) {
      if (isCharged()) {
        return 127.5;
      }
      return 64.5;
    }
    return 0;
  }

}
