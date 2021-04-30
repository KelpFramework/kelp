package de.pxav.kelp.implementation1_8.entity.type;

import de.pxav.kelp.core.entity.KelpEntityType;
import de.pxav.kelp.core.entity.type.SnowmanEntity;
import de.pxav.kelp.core.entity.version.EntityTypeVersionTemplate;
import de.pxav.kelp.implementation1_8.entity.type.general.VersionedGolem;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntitySnowman;
import org.bukkit.Location;

public class VersionedSnowman extends VersionedGolem<SnowmanEntity> implements SnowmanEntity {

  public VersionedSnowman(Entity entityHandle, KelpEntityType entityType, Location initialLocation, EntityTypeVersionTemplate entityTypeVersionTemplate) {
    super(entityHandle, entityType, initialLocation, entityTypeVersionTemplate);
  }

  // snowmen always have a pumpkin in 1.8
  @Override
  public boolean hasPumpkin() {
    return true;
  }

  @Override
  public SnowmanEntity setPumpkinHead(boolean pumpkinHead) {
    return this;
  }

}
