package de.pxav.kelp.core.entity.type.general;

import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.World;

public interface MonsterEntity<T extends MonsterEntity<?>> extends MobileEntity<T> {

  int getMaximumSpawnLightLevel();

  default boolean canSpawnInLightLevel(int lightLevel) {
    return getMaximumSpawnLightLevel() >= lightLevel;
  }

  int getAttackDamage(Difficulty difficulty);

  default int getAttackDamage() {
    World world = Bukkit.getWorld(getLocation().getWorldName());
    if (world == null) {
      return 0;
    }
    return getAttackDamage(world.getDifficulty());
  }

}
