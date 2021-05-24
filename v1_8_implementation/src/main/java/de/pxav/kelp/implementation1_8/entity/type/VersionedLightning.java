package de.pxav.kelp.implementation1_8.entity.type;

import de.pxav.kelp.core.entity.KelpEntityType;
import de.pxav.kelp.core.entity.type.LightningEntity;
import de.pxav.kelp.core.entity.version.EntityTypeVersionTemplate;
import de.pxav.kelp.implementation1_8.entity.VersionedEntity;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityLightning;
import org.bukkit.Location;

public class VersionedLightning extends VersionedEntity<LightningEntity> implements LightningEntity {

  private EntityLightning entityLightning;

  public VersionedLightning(Entity entityHandle, KelpEntityType entityType, Location initialLocation, EntityTypeVersionTemplate entityTypeVersionTemplate) {
    super(entityHandle, entityType, initialLocation, entityTypeVersionTemplate);
    this.entityLightning = (EntityLightning) entityHandle;
  }

  @Override
  public boolean isEffect() {
    return entityLightning.isEffect;
  }
}
