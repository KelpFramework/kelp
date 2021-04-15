package de.pxav.kelp.core.entity.type;

import de.pxav.kelp.core.KelpPlugin;
import de.pxav.kelp.core.entity.KelpEntityFactory;
import de.pxav.kelp.core.entity.KelpEntityType;
import de.pxav.kelp.core.entity.type.general.AgeableEntity;
import de.pxav.kelp.core.entity.type.general.MonsterEntity;
import de.pxav.kelp.core.world.KelpLocation;
import org.bukkit.Difficulty;

public interface ZombieEntity extends AgeableEntity<ZombieEntity>, MonsterEntity<ZombieEntity> {

  static ZombieEntity create(KelpLocation location) {
    return (ZombieEntity) KelpPlugin.getInjector().getInstance(KelpEntityFactory.class)
      .newKelpEntity(KelpEntityType.ZOMBIE, location.getBukkitLocation());
  }

  @Override
  default int getAttackDamage(Difficulty difficulty) {
    if (difficulty == Difficulty.EASY) {
      return 4;
    }
    if (difficulty == Difficulty.NORMAL) {
      return 6;
    }
    if (difficulty == Difficulty.HARD) {
      return 8;
    }
    return 0;
  }

  @Override
  default int getMaximumSpawnLightLevel() {
    return 7;
  }

}
