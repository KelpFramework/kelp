package de.pxav.kelp.core.entity.type.general;

import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftCreature;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftMob;

public interface MonsterEntity<T extends MonsterEntity<?>> extends MobileEntity<T> {

  default int getMaximumSpawnLightLevel() {
    return 0;
  }

  default boolean canSpawnInLightLevel(int lightLevel) {
    return getMaximumSpawnLightLevel() >= lightLevel;
  }

  default double getAttackDamage(Difficulty difficulty) {
    return 0;
  }

  default double getAttackDamage() {
    World world = Bukkit.getWorld(getLocation().getWorldName());
    if (world == null) {
      return 0.0d;
    }
    return getAttackDamage(world.getDifficulty());
  }

}
