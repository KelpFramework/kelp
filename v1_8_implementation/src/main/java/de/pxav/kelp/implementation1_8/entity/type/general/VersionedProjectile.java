package de.pxav.kelp.implementation1_8.entity.type.general;

import de.pxav.kelp.core.entity.KelpEntity;
import de.pxav.kelp.core.entity.KelpEntityType;
import de.pxav.kelp.core.entity.type.general.KelpProjectile;
import de.pxav.kelp.core.entity.type.general.ProjectileLauncher;
import de.pxav.kelp.core.entity.version.EntityTypeVersionTemplate;
import de.pxav.kelp.implementation1_8.entity.VersionedEntity;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityProjectile;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftProjectile;
import org.bukkit.projectiles.ProjectileSource;

public class VersionedProjectile<T extends KelpProjectile<T>>
  extends VersionedEntity<T>
  implements KelpProjectile<T> {

  private EntityProjectile projectileHandle;

  public VersionedProjectile(Entity entityHandle, KelpEntityType entityType, Location initialLocation, EntityTypeVersionTemplate entityTypeVersionTemplate) {
    super(entityHandle, entityType, initialLocation, entityTypeVersionTemplate);
    this.projectileHandle = (EntityProjectile) entityHandle;
  }

  //TODO: implement block sources such as dispensers, etc.
  @Override
  public T setLauncher(ProjectileLauncher<?> launcher) {
    if (launcher instanceof KelpEntity<?>) {
      KelpEntity<?> entity = (KelpEntity<?>) launcher;
      projectileHandle.projectileSource = (ProjectileSource) entity.getBukkitEntity();
    }
    return null;
  }

  @Override
  public ProjectileLauncher<?> getLauncher() {
    ProjectileSource source = projectileHandle.projectileSource;
    if (source instanceof org.bukkit.entity.Entity) {
      return (ProjectileLauncher<?>) entityTypeVersionTemplate.getKelpEntity((org.bukkit.entity.Entity) source);
    }
    return null;
  }

}
