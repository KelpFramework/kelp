package de.pxav.kelp.implementation1_8.entity.type;

import de.pxav.kelp.core.entity.KelpEntityType;
import de.pxav.kelp.core.entity.type.CaveSpiderEntity;
import de.pxav.kelp.core.entity.version.EntityTypeVersionTemplate;
import net.minecraft.server.v1_8_R3.Entity;
import org.bukkit.Location;

public class VersionedCaveSpider extends VersionedSpider implements CaveSpiderEntity {

  public VersionedCaveSpider(Entity entityHandle, KelpEntityType entityType, Location initialLocation, EntityTypeVersionTemplate entityTypeVersionTemplate) {
    super(entityHandle, entityType, initialLocation, entityTypeVersionTemplate);
  }

}
