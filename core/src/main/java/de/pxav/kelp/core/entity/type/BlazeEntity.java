package de.pxav.kelp.core.entity.type;

import de.pxav.kelp.core.entity.type.general.MonsterEntity;
import org.bukkit.Difficulty;

public interface BlazeEntity extends MonsterEntity<BlazeEntity> {

  @Override
  default int getMaximumSpawnLightLevel() {
    return -1;
  }

  @Override
  default double getAttackDamage(Difficulty difficulty) {
    if (difficulty == Difficulty.EASY) {
      return 4;
    } else if (difficulty == Difficulty.NORMAL) {
      return 5;
    } if (difficulty == Difficulty.HARD) {
      return 9;
    }
    return 0;
  }

}
