package de.pxav.kelp.implementation1_8.entity.type;

import de.pxav.kelp.core.entity.KelpEntityType;
import de.pxav.kelp.core.entity.type.WitherSkeletonEntity;
import de.pxav.kelp.core.entity.version.EntityTypeVersionTemplate;
import net.minecraft.server.v1_8_R3.Entity;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftSkeleton;
import org.bukkit.entity.Skeleton;

public class VersionedWitherSkeleton extends VersionedSkeleton implements WitherSkeletonEntity {

  public VersionedWitherSkeleton(Entity entityHandle, KelpEntityType entityType, Location initialLocation, EntityTypeVersionTemplate entityTypeVersionTemplate) {
    super(entityHandle, entityType, initialLocation, entityTypeVersionTemplate);
    ((CraftSkeleton)entityHandle.getBukkitEntity()).setSkeletonType(Skeleton.SkeletonType.WITHER);
  }

}
