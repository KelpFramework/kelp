package de.pxav.kelp.implementation1_8.entity.type.general;

import de.pxav.kelp.core.entity.KelpEntityType;
import de.pxav.kelp.core.entity.type.general.KelpProjectile;
import de.pxav.kelp.core.entity.type.general.ProjectileLauncher;
import de.pxav.kelp.implementation1_8.entity.VersionedEntity;
import net.minecraft.server.v1_8_R3.Entity;
import org.bukkit.Location;

public class VersionedProjectile<T extends KelpProjectile<T>>
  extends VersionedEntity<T>
  implements KelpProjectile<T> {

  public VersionedProjectile(Entity entityHandle, KelpEntityType entityType, Location initialLocation) {
    super(entityHandle, entityType, initialLocation);
  }

  @Override
  public T setLauncher(ProjectileLauncher<?> launcher) {
    return null;
  }

  @Override
  public ProjectileLauncher<?> getLauncher() {
    return null;
  }

}
