package de.pxav.kelp.core.entity.type;

import org.bukkit.Difficulty;

public interface CaveSpiderEntity extends SpiderEntity {

  @Override
  default int getMaximumSpawnLightLevel() {
    return 7;
  }

  @Override
  default double getAttackDamage(Difficulty difficulty) {
    if (difficulty == Difficulty.EASY) {
      return 2;
    } else if (difficulty == Difficulty.NORMAL) {
      return 2;
    } if (difficulty == Difficulty.HARD) {
      return 3;
    }
    return 0;
  }

}
