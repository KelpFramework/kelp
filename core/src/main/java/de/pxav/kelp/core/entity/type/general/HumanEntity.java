package de.pxav.kelp.core.entity.type.general;

import de.pxav.kelp.core.entity.LivingKelpEntity;
import de.pxav.kelp.core.world.KelpLocation;
import org.bukkit.GameMode;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftHumanEntity;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftVehicle;

public interface HumanEntity<T extends HumanEntity<?>> extends LivingKelpEntity<T> {

  // TODO: INVENTORIES

  GameMode getGameMode();

  T setGameMode(GameMode gameMode);

  // location of bed the player is currently sleeping in null if not sleeping
  KelpLocation getCurrentBedLocation();

  int getExpToLevel();

  // not set spawn loc
  T wakeUp();

  T wakeUpAndSetSpawnLocation();

  T sleep(KelpLocation bedLocation);

}
