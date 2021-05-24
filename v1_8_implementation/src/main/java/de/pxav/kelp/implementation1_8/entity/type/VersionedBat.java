package de.pxav.kelp.implementation1_8.entity.type;

import de.pxav.kelp.core.entity.KelpEntityType;
import de.pxav.kelp.core.entity.type.BatEntity;
import de.pxav.kelp.core.entity.version.EntityTypeVersionTemplate;
import de.pxav.kelp.implementation1_8.entity.type.general.VersionedMobileEntity;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityBat;
import org.bukkit.Location;

public class VersionedBat extends VersionedMobileEntity<BatEntity> implements BatEntity {

  private EntityBat batHandle;

  public VersionedBat(Entity entityHandle, KelpEntityType entityType, Location initialLocation, EntityTypeVersionTemplate entityTypeVersionTemplate) {
    super(entityHandle, entityType, initialLocation, entityTypeVersionTemplate);
    this.batHandle = (EntityBat) entityHandle;
  }

  @Override
  public boolean isAwake() {
    return !batHandle.isAsleep();
  }

  @Override
  public BatEntity setAwake(boolean awake) {
    batHandle.setAsleep(!awake);
    return this;
  }

}
