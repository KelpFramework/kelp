package de.pxav.kelp.implementation1_8.entity.type;

import de.pxav.kelp.core.entity.KelpEntityType;
import de.pxav.kelp.core.entity.version.EntityTypeVersionTemplate;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityGuardian;
import org.bukkit.Location;

public class VersionedElderGuardian extends VersionedGuardian {

  public VersionedElderGuardian(Entity entityHandle, KelpEntityType entityType, Location initialLocation, EntityTypeVersionTemplate entityTypeVersionTemplate) {
    super(entityHandle, entityType, initialLocation, entityTypeVersionTemplate);
    ((EntityGuardian)entityHandle).setElder(true);
  }

}
