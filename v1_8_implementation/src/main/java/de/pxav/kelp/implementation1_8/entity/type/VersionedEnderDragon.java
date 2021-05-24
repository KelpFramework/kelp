package de.pxav.kelp.implementation1_8.entity.type;

import de.pxav.kelp.core.entity.KelpEntityType;
import de.pxav.kelp.core.entity.type.EnderDragonEntity;
import de.pxav.kelp.core.entity.version.EntityTypeVersionTemplate;
import de.pxav.kelp.implementation1_8.entity.VersionedLivingEntity;
import net.minecraft.server.v1_8_R3.Entity;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEnderDragon;

public class VersionedEnderDragon extends VersionedLivingEntity<EnderDragonEntity> implements EnderDragonEntity {

  CraftEnderDragon craftEnderDragon;

  public VersionedEnderDragon(Entity entityHandle, KelpEntityType entityType, Location initialLocation, EntityTypeVersionTemplate entityTypeVersionTemplate) {
    super(entityHandle, entityType, initialLocation, entityTypeVersionTemplate);
    this.craftEnderDragon = (CraftEnderDragon) entityHandle.getBukkitEntity();
  }

  @Override
  public int getDeathAnimationInTicks() {
    return craftEnderDragon.getHandle().deathTicks;
  }

}
