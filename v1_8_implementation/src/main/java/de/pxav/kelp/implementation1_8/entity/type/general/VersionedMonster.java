package de.pxav.kelp.implementation1_8.entity.type.general;

import de.pxav.kelp.core.entity.KelpEntityType;
import de.pxav.kelp.core.entity.type.general.MobileEntity;
import de.pxav.kelp.core.entity.type.general.MonsterEntity;
import de.pxav.kelp.core.entity.version.EntityTypeVersionTemplate;
import net.minecraft.server.v1_8_R3.Entity;
import org.bukkit.Location;

public class VersionedMonster<T extends MonsterEntity<T>> extends VersionedMobileEntity<T> implements MobileEntity<T> {

  public VersionedMonster(Entity entityHandle, KelpEntityType entityType, Location initialLocation, EntityTypeVersionTemplate entityTypeVersionTemplate) {
    super(entityHandle, entityType, initialLocation, entityTypeVersionTemplate);
  }

}
