package de.pxav.kelp.core.entity.type.general;

public interface ProjectileLauncher<T extends ProjectileLauncher<T>> {

  T launchProjectile(KelpProjectile<?> projectileType);

}
