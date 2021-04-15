package de.pxav.kelp.core.entity.type.general;

public interface ProjectileLauncher<T extends ProjectileLauncher<T>> {

  <E extends KelpProjectile<?>> E launchProjectile(Class<? extends KelpProjectile<?>> projectileType);

}
