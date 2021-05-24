package de.pxav.kelp.implementation1_8.entity.type;

import de.pxav.kelp.core.entity.KelpEntityType;
import de.pxav.kelp.core.entity.type.PaintingEntity;
import de.pxav.kelp.core.entity.util.PaintingType;
import de.pxav.kelp.core.entity.version.EntityConstantsVersionTemplate;
import de.pxav.kelp.core.entity.version.EntityTypeVersionTemplate;
import de.pxav.kelp.implementation1_8.entity.type.general.VersionedHangingEntity;
import net.minecraft.server.v1_8_R3.Entity;
import org.bukkit.Art;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPainting;

public class VersionedPainting extends VersionedHangingEntity<PaintingEntity> implements PaintingEntity {

  private CraftPainting craftPainting;
  private EntityConstantsVersionTemplate entityConstantsVersionTemplate;

  public VersionedPainting(Entity entityHandle, KelpEntityType entityType, Location initialLocation, EntityTypeVersionTemplate entityTypeVersionTemplate, EntityConstantsVersionTemplate entityConstantsVersionTemplate) {
    super(entityHandle, entityType, initialLocation, entityTypeVersionTemplate);
    this.craftPainting = (CraftPainting) entityHandle.getBukkitEntity();
    this.entityConstantsVersionTemplate = entityConstantsVersionTemplate;
  }

  @Override
  public PaintingEntity setPaintingType(PaintingType type) {
    craftPainting.setArt(entityConstantsVersionTemplate.getBukkitPaintingType(type));
    return this;
  }

  @Override
  public PaintingType getPaintingType() {
    return entityConstantsVersionTemplate.getPaintingType(craftPainting.getArt());
  }

}
