package de.pxav.kelp.core.world.raycast;

import de.pxav.kelp.core.world.KelpLocation;

public interface RaycastHit {

  // redundant if you only have one ray type but if you have several, it might be useful
  Ray<?> getCollidingRay();

  Object getCollider();

  double getHitDistance();

  KelpLocation getPoint();

}
