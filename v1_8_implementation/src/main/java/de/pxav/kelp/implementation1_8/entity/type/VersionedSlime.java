package de.pxav.kelp.implementation1_8.entity.type;

import de.pxav.kelp.core.entity.KelpEntityType;
import de.pxav.kelp.core.entity.type.SlimeEntity;
import de.pxav.kelp.core.entity.version.EntityTypeVersionTemplate;
import de.pxav.kelp.implementation1_8.entity.type.general.VersionedMobileEntity;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntitySlice;
import net.minecraft.server.v1_8_R3.EntitySlime;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftSlime;

public class VersionedSlime extends VersionedMobileEntity<SlimeEntity> implements SlimeEntity {

  private EntitySlime entitySlime;

  public VersionedSlime(Entity entityHandle, KelpEntityType entityType, Location initialLocation, EntityTypeVersionTemplate entityTypeVersionTemplate) {
    super(entityHandle, entityType, initialLocation, entityTypeVersionTemplate);
    this.entitySlime = (EntitySlime) entityHandle;
  }

  @Override
  public int getSlimeSize() {
    return entitySlime.getSize();
  }

  @Override
  public SlimeEntity setSlimeSize(int slimeSize) {
    entitySlime.setSize(slimeSize);
    return this;
  }
}
