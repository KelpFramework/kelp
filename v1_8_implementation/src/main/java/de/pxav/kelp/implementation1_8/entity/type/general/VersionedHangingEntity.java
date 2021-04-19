package de.pxav.kelp.implementation1_8.entity.type.general;

import de.pxav.kelp.core.entity.KelpEntityType;
import de.pxav.kelp.core.entity.type.general.HangingEntity;
import de.pxav.kelp.core.entity.version.EntityTypeVersionTemplate;
import de.pxav.kelp.core.world.KelpBlock;
import de.pxav.kelp.core.world.util.KelpBlockFace;
import de.pxav.kelp.implementation1_8.entity.VersionedEntity;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityHanging;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftHanging;
import org.bukkit.material.Attachable;

public class VersionedHangingEntity<T extends HangingEntity<T>>
  extends VersionedEntity<T>
  implements HangingEntity<T> {

  private CraftHanging craftHanging;

  public VersionedHangingEntity(Entity entityHandle, KelpEntityType entityType, Location initialLocation, EntityTypeVersionTemplate entityTypeVersionTemplate) {
    super(entityHandle, entityType, initialLocation, entityTypeVersionTemplate);
    this.craftHanging = (CraftHanging) entityHandle.getBukkitEntity();
  }

  @Override
  public KelpBlockFace getAttachedFace() {
    return KelpBlockFace.from(craftHanging.getAttachedFace());
  }

  @Override
  public KelpBlock getAttachedBlock() {
    return getLocation().getBlock().getRelative(getAttachedFace());
  }

  @Override
  public T setFacingDirection(KelpBlockFace blockFace) {
    craftHanging.setFacingDirection(blockFace.getBukkitFace(), true);
    return (T) this;
  }

}
