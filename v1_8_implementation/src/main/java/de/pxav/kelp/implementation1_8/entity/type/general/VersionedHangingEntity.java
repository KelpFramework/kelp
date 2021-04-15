package de.pxav.kelp.implementation1_8.entity.type.general;

import de.pxav.kelp.core.entity.KelpEntityType;
import de.pxav.kelp.core.entity.type.general.HangingEntity;
import de.pxav.kelp.core.world.KelpBlock;
import de.pxav.kelp.core.world.util.KelpBlockFace;
import de.pxav.kelp.implementation1_8.entity.VersionedEntity;
import net.minecraft.server.v1_8_R3.Entity;
import org.bukkit.Location;

public class VersionedHangingEntity<T extends HangingEntity<T>>
  extends VersionedEntity<T>
  implements HangingEntity<T> {

  public VersionedHangingEntity(Entity entityHandle, KelpEntityType entityType, Location initialLocation) {
    super(entityHandle, entityType, initialLocation);
  }

  @Override
  public KelpBlockFace getAttachedFace() {
    return null;
  }

  @Override
  public KelpBlock getAttachedBlock() {
    return null;
  }

  @Override
  public T setFacingDirection(KelpBlockFace blockFace) {
    return null;
  }

}
