package de.pxav.kelp.implementation1_8.entity.type;

import de.pxav.kelp.core.entity.KelpEntityType;
import de.pxav.kelp.core.entity.type.LeashHitchEntity;
import de.pxav.kelp.core.entity.version.EntityTypeVersionTemplate;
import de.pxav.kelp.implementation1_8.entity.type.general.VersionedHangingEntity;
import net.minecraft.server.v1_8_R3.Entity;
import org.bukkit.Location;

public class VersionedLeashHitch extends VersionedHangingEntity<LeashHitchEntity> implements LeashHitchEntity {

  public VersionedLeashHitch(Entity entityHandle, KelpEntityType entityType, Location initialLocation, EntityTypeVersionTemplate entityTypeVersionTemplate) {
    super(entityHandle, entityType, initialLocation, entityTypeVersionTemplate);
  }

}
