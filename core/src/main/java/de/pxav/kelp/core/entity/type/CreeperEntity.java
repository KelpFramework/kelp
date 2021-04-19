package de.pxav.kelp.core.entity.type;

import de.pxav.kelp.core.entity.type.general.MonsterEntity;
import org.bukkit.Difficulty;

public interface CreeperEntity extends MonsterEntity<CreeperEntity> {

  boolean isCharged();

  void setCharged(boolean charged);

  void setMaxFuseTicks(int maxFuseTicks);

  int getMaxFuseTicks();

  void setFuseTicks(int fuseTicks);

  int getFuseTicks();

  void setExplosionRadius(int explosionRadius);

  int getExplosionRadius();

  void explode();

  void ignite();

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
