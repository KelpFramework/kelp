package de.pxav.kelp.implementation1_8.entity.type;

import de.pxav.kelp.core.entity.KelpEntityType;
import de.pxav.kelp.core.entity.type.ThrownWitherSkullEntity;
import de.pxav.kelp.core.entity.version.EntityTypeVersionTemplate;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityWitherSkull;
import org.bukkit.Location;

public class VersionedThrownWitherSkull extends VersionedThrownFireball implements ThrownWitherSkullEntity {

  private EntityWitherSkull witherSkull;

  public VersionedThrownWitherSkull(Entity entityHandle, KelpEntityType entityType, Location initialLocation, EntityTypeVersionTemplate entityTypeVersionTemplate) {
    super(entityHandle, entityType, initialLocation, entityTypeVersionTemplate);
    this.witherSkull = (EntityWitherSkull) entityHandle;
  }

  @Override
  public boolean isCharged() {
    return witherSkull.isCharged();
  }

  @Override
  public ThrownWitherSkullEntity setCharged(boolean charged) {
    witherSkull.setCharged(charged);
    return this;
  }

}
