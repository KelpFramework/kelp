package de.pxav.kelp.implementation1_8.entity.type.general;

import de.pxav.kelp.core.entity.KelpEntityType;
import de.pxav.kelp.core.entity.type.general.HumanEntity;
import de.pxav.kelp.core.world.KelpLocation;
import net.minecraft.server.v1_8_R3.Entity;
import org.bukkit.GameMode;
import org.bukkit.Location;

public class VersionedHumanEntity<T extends HumanEntity<T>>
  extends VersionedMobileEntity<T>
  implements HumanEntity<T> {

  public VersionedHumanEntity(Entity entityHandle, KelpEntityType entityType, Location initialLocation) {
    super(entityHandle, entityType, initialLocation);
  }

  @Override
  public GameMode getGameMode() {
    return null;
  }

  @Override
  public T setGameMode() {
    return null;
  }

  @Override
  public KelpLocation getCurrentBedLocation() {
    return null;
  }

  @Override
  public int getExpToLevel() {
    return 0;
  }

  @Override
  public T wakeUp() {
    return null;
  }

  @Override
  public T wakeUpAndSetSpawnLocation() {
    return null;
  }

  @Override
  public T sleep(KelpLocation bedLocation) {
    return null;
  }
}
