package de.pxav.kelp.implementation1_8.entity.type;

import de.pxav.kelp.core.entity.KelpEntityType;
import de.pxav.kelp.core.entity.type.EnderCrystalEntity;
import de.pxav.kelp.core.entity.version.EntityTypeVersionTemplate;
import de.pxav.kelp.core.world.KelpLocation;
import de.pxav.kelp.implementation1_8.entity.VersionedEntity;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityEnderCrystal;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEnderCrystal;

public class VersionedEnderCrystal extends VersionedEntity<EnderCrystalEntity> implements EnderCrystalEntity {

  private CraftEnderCrystal craftEnderCrystal;
  private EntityEnderCrystal crystalHandle;

  // none of the methods exists in mc 1.8. Not even in the corresponding NMS class :(
  public VersionedEnderCrystal(Entity entityHandle, KelpEntityType entityType, Location initialLocation, EntityTypeVersionTemplate entityTypeVersionTemplate) {
    super(entityHandle, entityType, initialLocation, entityTypeVersionTemplate);
  }

  @Override
  public boolean isShowingBottom() {
    return true;
  }

  @Override
  public EnderCrystalEntity setShowingBottom(boolean showingBottom) {
    return this;
  }

  @Override
  public KelpLocation getBeamTarget() {
    return null;
  }

  @Override
  public EnderCrystalEntity setBeamTarget(KelpLocation beamTarget) {
    return this;
  }

}
