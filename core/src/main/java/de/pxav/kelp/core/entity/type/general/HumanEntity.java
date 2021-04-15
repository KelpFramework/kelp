package de.pxav.kelp.core.entity.type.general;

import de.pxav.kelp.core.world.KelpLocation;
import org.bukkit.GameMode;

public interface HumanEntity<T extends HumanEntity<?>> extends MobileEntity<T> {

  // TODO: INVENTORIES

  GameMode getGameMode();

  T setGameMode();

  // location of bed the player is currently sleeping in null if not sleeping
  KelpLocation getCurrentBedLocation();

  int getExpToLevel();

  // not set spawn loc
  T wakeUp();

  T wakeUpAndSetSpawnLocation();

  T sleep(KelpLocation bedLocation);

}
