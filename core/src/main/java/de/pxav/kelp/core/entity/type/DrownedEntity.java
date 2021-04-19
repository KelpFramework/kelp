package de.pxav.kelp.core.entity.type;

import org.bukkit.Difficulty;

public interface DrownedEntity extends ZombieEntity {

  @Override
  default int getMaximumSpawnLightLevel() {
    return 7;
  }

  @Override
  default double getAttackDamage(Difficulty difficulty) {
    if (difficulty == Difficulty.EASY) {
      return 2.5;
    } else if (difficulty == Difficulty.NORMAL) {
      return 3;
    } if (difficulty == Difficulty.HARD) {
      return 4.5;
    }
    return 0;
  }

}
