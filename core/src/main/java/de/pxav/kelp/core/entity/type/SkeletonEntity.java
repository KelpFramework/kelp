package de.pxav.kelp.core.entity.type;

import de.pxav.kelp.core.entity.type.general.MonsterEntity;
import org.bukkit.Difficulty;

public interface SkeletonEntity extends MonsterEntity<SkeletonEntity> {

  //todo maybe check how many arrows left if possible in minecraft

  @Override
  default int getMaximumSpawnLightLevel() {
    return 7;
  }

  @Override
  default double getAttackDamage(Difficulty difficulty) {
    return 0;
  }

}
