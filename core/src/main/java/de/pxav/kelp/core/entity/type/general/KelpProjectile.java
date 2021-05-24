package de.pxav.kelp.core.entity.type.general;

import de.pxav.kelp.core.entity.KelpEntity;

public interface KelpProjectile<T extends KelpProjectile<?>> extends KelpEntity<T> {

  T setLauncher(ProjectileLauncher<?> launcher);

  ProjectileLauncher<?> getLauncher();

}
